package RazerOfficial.Razer.gg.module.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.player.AttackEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import util.time.StopWatch;

@Razer
@ModuleInfo(name = "module.player.autopot.name", description = "module.player.autopot.description", category = Category.PLAYER)
public class AutoPot extends Module {

    private final NumberValue health = new NumberValue("Health", this, 15, 1, 20, 1);
    private final BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 500, 1000, 50, 5000, 50);
    private final BoundsNumberValue rotationSpeed = new BoundsNumberValue("Rotation Speed", this, 5, 10, 0, 10, 1);
    private final StopWatch stopWatch = new StopWatch();

    private int attackTicks;
    private boolean splash;
    private long nextThrow;


    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        this.attackTicks++;

        if (mc.thePlayer.onGroundTicks <= 1 || attackTicks < 10 || this.getModule(Scaffold.class).isEnabled()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack == null) {
                continue;
            }

            final Item item = stack.getItem();

            if (item instanceof ItemPotion) {
                final ItemPotion potion = (ItemPotion) item;
                final PotionEffect effect = potion.getEffects(stack).get(0);

                if (!ItemPotion.isSplash(stack.getMetadata()) ||
                        !PlayerUtil.goodPotion(effect.getPotionID()) ||
                        (effect.getPotionID() == Potion.regeneration.id ||
                                effect.getPotionID() == Potion.heal.id) &&
                                mc.thePlayer.getHealth() > this.health.getValue().floatValue()) {
                    continue;
                }

                if (mc.thePlayer.isPotionActive(effect.potionID) &&
                        mc.thePlayer.getActivePotionEffect(effect.potionID).duration != 0) {
                    continue;
                }

                final double minRotationSpeed = this.rotationSpeed.getValue().doubleValue();
                final double maxRotationSpeed = this.rotationSpeed.getSecondValue().doubleValue();
                final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);
                RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), rotationSpeed, MovementFix.NORMAL);

                SlotComponent.setSlot(i);

                if (!this.splash) {
                    this.splash = true;
                    return;
                }

                if (RotationComponent.rotations.y > 85 && SlotComponent.getItemStack().getItem() == item &&
                        !BadPacketsComponent.bad() && stopWatch.finished(nextThrow)) {
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));

                    this.nextThrow = Math.round(MathUtil.getRandom(delay.getValue().longValue(), delay.getSecondValue().longValue()));
                    this.splash = false;
                    stopWatch.reset();
                }
            }
        }
    };


    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        this.attackTicks = 0;
    };
}