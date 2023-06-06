package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.GUIDetectionComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.math.MathUtil;
import me.neilhuang007.razer.util.player.ItemUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import util.time.StopWatch;


@Rise
@ModuleInfo(name = "module.player.stealer.name", description = "module.player.stealer.description", category = Category.PLAYER)
public class Stealer extends Module {

    private final BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 100, 150, 0, 500, 50);
    private final BooleanValue ignoreTrash = new BooleanValue("Ignore Trash", this, true);

    private final StopWatch stopwatch = new StopWatch();
    private long nextClick;
    private int lastClick;
    private int lastSteal;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.currentScreen instanceof GuiChest) {
            final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;

            if (GUIDetectionComponent.inGUI() || !this.stopwatch.finished(this.nextClick)) {
                return;
            }

            this.lastSteal++;

            for (int i = 0; i < container.inventorySlots.size(); i++) {
                final ItemStack stack = container.getLowerChestInventory().getStackInSlot(i);

                if (stack == null || this.lastSteal <= 1) {
                    continue;
                }

                if (this.ignoreTrash.getValue() && !ItemUtil.useful(stack)) {
                    continue;
                }

                this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
                mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                this.stopwatch.reset();
                this.lastClick = 0;
                if (this.nextClick > 0) return;
            }

            this.lastClick++;

            if (this.lastClick > 1) {
                mc.thePlayer.closeScreen();
            }
        } else {
            this.lastClick = 0;
            this.lastSteal = 0;
        }
    };
}