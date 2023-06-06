package me.neilhuang007.razer.component.impl.render.espcomponent.impl;

import me.neilhuang007.razer.component.impl.render.espcomponent.api.ESP;
import me.neilhuang007.razer.component.impl.render.espcomponent.api.ESPColor;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.render.ColorUtil;
import me.neilhuang007.razer.util.render.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class PlayerChams extends ESP implements InstanceAccess {

    public PlayerChams(ESPColor espColor) {
        super(espColor);
    }

    @Override
    public void render3D() {
        final float partialTicks = mc.timer.renderPartialTicks;
        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            final Render<EntityPlayer> render = mc.getRenderManager().getEntityRenderObject(player);

            if (mc.getRenderManager() == null || render == null || (player == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) || !RenderUtil.isInViewFrustrum(player) || player.isDead) {
                continue;
            }

            final Color color = player.hurtTime > 0 ? Color.RED : ColorUtil.mixColors(getColor(player), Color.WHITE, 0.2);

            if (color.getAlpha() <= 0) {
                continue;
            }

            final double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
            final double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
            final double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
            final float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;

            RendererLivingEntity.setShaderBrightness(color);
            render.doRender(player, x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ, yaw, partialTicks);
            RendererLivingEntity.unsetShaderBrightness();

            player.hide();
        }

        RenderHelper.disableStandardItemLighting();
        mc.entityRenderer.disableLightmap();
    }
}
