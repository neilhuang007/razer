package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.render.ProjectionComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.Render2DEvent;
import me.neilhuang007.razer.util.vector.Vector2d;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector4d;
import java.awt.*;

import static me.neilhuang007.razer.util.render.RenderUtil.*;

/**
 * @author Hazsi, Alan
 * @since 10/11/2022
 */
@Rise
@ModuleInfo(name = "module.render.2desp.name", description = "module.render.projectionesp.description", category = Category.RENDER)
public class ProjectionESP extends Module {

    public BooleanValue glow = new BooleanValue("Glow", this, true);

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (mc.getRenderManager() == null || player == mc.thePlayer ||
                    !isInViewFrustrum(player) || player.isDead || player.isInvisible()) {
                continue;
            }

            Vector4d pos = ProjectionComponent.get(player);

            if (pos == null) {
                return;
            }

            // Black outline
            rectangle(pos.x, pos.y, pos.z - pos.x, 1.5, Color.BLACK); // Top
            rectangle(pos.x, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Left
            rectangle(pos.z, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Right
            rectangle(pos.x, pos.w, pos.z - pos.x, 1.5, Color.BLACK); // Bottom

            // Main ESP
            Runnable runnable = () -> {

                final Vector2d first = new Vector2d(0, 0), second = new Vector2d(0, 500);

                horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                        this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                        this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                        this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));
                horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                        this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));
            };

            runnable.run();
            if (this.glow.getValue()) {
                NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
            }
        }
    };
}
