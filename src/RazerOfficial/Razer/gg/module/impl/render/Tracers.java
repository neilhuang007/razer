package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import java.awt.*;

@Razer
@ModuleInfo(name = "module.render.tracers.name", description = "module.render.tracers.description", category = Category.RENDER)
public final class Tracers extends Module {

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (mc.gameSettings.hideGUI) {
            ChatUtil.display(ChatFormatting.RED + "HideGUI Enabled Module Detoggled");
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);

        for (final Entity player : RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager()) {
//            if (player == mc.thePlayer || player.isDead || RazerOfficial.Razer.gg.Razer.INSTANCE.getBotManager().contains(player)) {
//                continue;
//            }

            final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
            final double y = (player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks()) + 1.62F;
            final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

            final Color color = ColorUtil.withAlpha(
                    ColorUtil.mixColors(getTheme().getSecondColor(), getTheme().getFirstColor(), Math.min(1, mc.thePlayer.getDistanceToEntity(player) / 50)),
                128);

            RenderUtil.drawLine(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY + mc.thePlayer.getEyeHeight(), mc.getRenderManager().renderPosZ, x, y, z, color, 1.5F);
        }

        GlStateManager.resetColor();
        GlStateManager.popMatrix();
    };
}