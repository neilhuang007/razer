package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.BadPacketsComponent;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.math.MathUtil;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import util.time.StopWatch;

@Rise
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