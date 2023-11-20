package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.render.ProjectionComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector4d;
import java.awt.*;

/**
 * @author Hazsi, Alan
 * @since 10/11/2022
 */
@Razer
@ModuleInfo(name = "module.render.2desp.name", description = "module.render.projectionesp.description", category = Category.RENDER)
public class ProjectionESP extends Module {

    public BooleanValue glow = new BooleanValue("Glow", this, true);

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        for (EntityPlayer player : InstanceAccess.mc.theWorld.playerEntities) {
            if (InstanceAccess.mc.getRenderManager() == null || player == InstanceAccess.mc.thePlayer ||
                    !RenderUtil.isInViewFrustrum(player) || player.isDead || player.isInvisible()) {
                continue;
            }

            Vector4d pos = ProjectionComponent.get(player);

            if (pos == null) {
                return;
            }

            // Black outline
            RenderUtil.rectangle(pos.x, pos.y, pos.z - pos.x, 1.5, Color.BLACK); // Top
            RenderUtil.rectangle(pos.x, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Left
            RenderUtil.rectangle(pos.z, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Right
            RenderUtil.rectangle(pos.x, pos.w, pos.z - pos.x, 1.5, Color.BLACK); // Bottom

            // Main ESP
            Runnable runnable = () -> {

                final Vector2d first = new Vector2d(0, 0), second = new Vector2d(0, 500);

                RenderUtil.horizontalGradient(pos.x + 0.5, pos.y + 0.5, pos.z - pos.x, 0.5, // Top
                        this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                RenderUtil.verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Left
                        this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                RenderUtil.verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, // Right
                        this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));
                RenderUtil.horizontalGradient(pos.x + 0.5, pos.w + 0.5, pos.z - pos.x, 0.5, // Bottom
                        this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));
            };

            runnable.run();
            if (this.glow.getValue()) {
                InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
            }
        }
    };
}
