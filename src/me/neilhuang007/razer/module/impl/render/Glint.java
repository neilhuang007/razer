package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.GlintEvent;
import me.neilhuang007.razer.newevent.impl.render.Render3DEvent;
import me.neilhuang007.razer.util.render.ColorUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.src.Config;
import net.optifine.CustomItems;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;

import java.awt.*;

/**
 * @author Alan
 * @since 15.03.2022
 */
@ModuleInfo(name = "module.render.glint.name", description = "module.render.glint.description", category = Category.RENDER)
public final class Glint extends Module {

    private final BooleanValue glintWeapons = new BooleanValue("Glint Weapons", this, true);
    private final BoundsNumberValue hue = new BoundsNumberValue("Hue", this, 0, 360, 0, 360, 1);
    private final NumberValue layers = new NumberValue("Layers", this, 4, 1, 8, 1);
//    private final BooleanValue glow = new BooleanValue("Glow", this, true);

    @EventLink()
    public final Listener<GlintEvent> onGlint = event -> {

        final ItemStack itemStack = event.getItemStack();
        final Item item = itemStack.getItem();

        if (this.glintWeapons.getValue() && (item instanceof ItemSword || item instanceof ItemAxe)) {
            event.setEnchanted(true);
        }

        event.setCancelled(true);

        if (event.isEnchanted() && event.isRender()) {
            this.renderEffect(event.getModel());
        }
    };

    public void renderEffect(final IBakedModel model) {
        if (RendererLivingEntity.SHADER_RENDERING) return;

        if (!Config.isCustomItems() || CustomItems.isUseGlint()) {
            if (!Config.isShaders() || !Shaders.isShadowPass) {
                GlStateManager.depthMask(false);
                GlStateManager.depthFunc(514);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(768, 1);
                mc.getRenderItem().textureManager.bindTexture(RenderItem.RES_ITEM_GLINT);

                if (Config.isShaders() && !mc.getRenderItem().renderItemGui) {
                    ShadersRender.renderEnchantedGlintBegin();
                }

                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();

                GlStateManager.scale(8.0F, 8.0F, 8.0F);
                final float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
                GlStateManager.translate(f, 0.0F, 0.0F);

                final Color firstColor = new Color(Color.HSBtoRGB(this.hue.getValue().intValue() / 255f, 0.6f, 1));
                final Color secondColor = new Color(Color.HSBtoRGB(this.hue.getSecondValue().intValue() / 255f, 0.6f, 1));

                for (int layer = 1; layer <= layers.getValue().intValue(); layer++) {
                    GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
                    mc.getRenderItem().renderModel(model, ColorUtil.mixColors(firstColor, secondColor, (layer / layers.getValue().floatValue())).hashCode());
                }

                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.enableLighting();
                GlStateManager.depthFunc(515);
                GlStateManager.depthMask(true);
                mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);

                if (Config.isShaders() && !mc.getRenderItem().renderItemGui) {
                    ShadersRender.renderEnchantedGlintEnd();
                }
            }
        }
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        // Removed because idk how to fix
//        if (this.glow.getValue()) {
//
//            Runnable runnable = () -> {
//                RendererLivingEntity.setShaderBrightness(this.getTheme().getFirstColor());
//                mc.entityRenderer.renderHand(mc.timer.renderPartialTicks, 2, true, true, true);
//                RendererLivingEntity.unsetShaderBrightness();
//            };
//
//            NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
//            NORMAL_OUTLINE_RUNNABLES.add(runnable);
//        }
    };
}
