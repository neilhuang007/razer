package RazerOfficial.Razer.gg.module.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import util.time.StopWatch;

@Razer
@ModuleInfo(name = "module.player.autohead.name", description = "module.player.autohead.description", category = Category.PLAYER)
public class AutoHead extends Module {

    private final NumberValue health = new NumberValue("Health", this, 15, 1, 20, 1);
    private final BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 500, 1000, 50, 5000, 50);

    private final StopWatch stopWatch = new StopWatch();

    private long nextUse;


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (this.getModule(Scaffold.class).isEnabled()) {
            return;
        }

        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack == null) {
                continue;
            }

            final Item item = stack.getItem();

            if (item instanceof ItemSkull) {
                if (mc.thePlayer.getHealth() > this.health.getValue().floatValue()) {
                    continue;
                }

                SlotComponent.setSlot(i);

                if (!BadPacketsComponent.bad() && stopWatch.finished(nextUse)) {
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));

                    nextUse = Math.round(MathUtil.getRandom(delay.getValue().longValue(), delay.getSecondValue().longValue()));
                    stopWatch.reset();
                }
            }
        }
    };
}