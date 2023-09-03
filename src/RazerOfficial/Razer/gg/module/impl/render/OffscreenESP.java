package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.render.ProjectionComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.LimitedRender2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.value.impl.ColorValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector4d;
import java.awt.*;

@Rise
@ModuleInfo(name = "module.render.offscreenesp.name", description = "module.render.offscreenesp.description", category = Category.RENDER)
public final class OffscreenESP extends Module {

    private ColorValue color = new ColorValue("Color", this, Color.RED);

    @EventLink()
    public final Listener<LimitedRender2DEvent> onLimitedRender2D = event -> {

        for (EntityPlayer player : InstanceAccess.mc.theWorld.playerEntities) {
            if (InstanceAccess.mc.getRenderManager() == null || (player == InstanceAccess.mc.thePlayer && InstanceAccess.mc.gameSettings.thirdPersonView == 0) ||
                    RenderUtil.isInViewFrustrum(player) || player.isDead || player.isInvisible()) {
                continue;
            }

            Vector4d position = ProjectionComponent.get(player);

            if (position == null) {
                return;
            }

            Vector2d middlePosition = new Vector2d(position.x + (position.z - position.x) / 2, position.y + (position.w - position.y) / 2);

            double angle = Math.toDegrees(Math.atan2(middlePosition.x, middlePosition.y));

            ChatUtil.display(player.getCommandSenderName() + " " + angle);
            GlStateManager.pushMatrix();
            GlStateManager.rotate((float) angle, 0, 1, 0);
            GlStateManager.translate(event.getScaledResolution().getScaledWidth() / 2f, event.getScaledResolution().getScaledHeight() / 2f, 0);
            RenderUtil.rectangle(0, 0, 1, 10, Color.BLACK);
            GlStateManager.popMatrix();
        }
    };
}