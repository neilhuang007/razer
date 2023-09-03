package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
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