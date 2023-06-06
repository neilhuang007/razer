package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Mouse;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.guiclicker.name", description = "module.ghost.guiclicker.description", category = Category.GHOST)
public class GuiClicker extends Module {

    public int mouseDownTicks;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.currentScreen instanceof GuiContainer) {
            GuiContainer container = ((GuiContainer) mc.currentScreen);

            final int i = Mouse.getEventX() * container.width / this.mc.displayWidth;
            final int j = container.height - Mouse.getEventY() * container.height / this.mc.displayHeight - 1;

            try {
                if (Mouse.isButtonDown(0)) {
                    mouseDownTicks++;
                    if (mouseDownTicks > 2 && Math.random() > 0.1) container.mouseClicked(i, j, 0);
                } else if (Mouse.isButtonDown(1)) {
                    mouseDownTicks++;
                    if (mouseDownTicks > 2 && Math.random() > 0.1) container.mouseClicked(i, j, 1);
                } else {
                    mouseDownTicks = 0;
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    };

}