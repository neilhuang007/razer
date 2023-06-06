package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.ghost.autoclicker.DragClickAutoClicker;
import me.neilhuang007.razer.module.impl.ghost.autoclicker.NormalAutoClicker;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.ClickEvent;
import me.neilhuang007.razer.newevent.impl.render.Render3DEvent;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.ModeValue;
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