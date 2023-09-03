package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.ClickEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.ghost.autoclicker.DragClickAutoClicker;
import RazerOfficial.Razer.gg.module.impl.ghost.autoclicker.NormalAutoClicker;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import util.time.StopWatch;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.autoclicker.name", description = "module.ghost.autoclicker.description", category = Category.GHOST)
public class AutoClicker extends Module {

    private ModeValue mode = new ModeValue("Mode", this)
            .add(new NormalAutoClicker("Normal", this))
            .add(new DragClickAutoClicker("Drag Click Simulations", this))
            .setDefault("Normal");

    private BooleanValue jitter = new BooleanValue("Jitter", this, false);

    private StopWatch stopWatch = new StopWatch();
    private double directionX, directionY;

    @EventLink
    public final Listener<ClickEvent> onClick = event -> {
        stopWatch.reset();

        directionX = (Math.random() - 0.5) * 4;
        directionY = (Math.random() - 0.5) * 4;
    };

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (!stopWatch.finished(100) && this.jitter.getValue() && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            EntityRenderer.mouseAddedX = (float) (((Math.random() - 0.5) * 400 / Minecraft.getDebugFPS()) * directionX);
            EntityRenderer.mouseAddedY = (float) (((Math.random() - 0.5) * 400 / Minecraft.getDebugFPS()) * directionY);
        }
    };

}