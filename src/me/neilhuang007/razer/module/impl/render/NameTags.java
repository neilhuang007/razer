package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.render.ProjectionComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.Render2DEvent;
import me.neilhuang007.razer.util.font.Font;
import me.neilhuang007.razer.util.font.FontManager;
import me.neilhuang007.razer.util.font.impl.minecraft.FontRenderer;
import me.neilhuang007.razer.util.render.RenderUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector4d;
import java.awt.*;

/**
 * @author Alan
 * @since 29/04/2022
 */
@Rise
@ModuleInfo(name = "module.render.nametags.name", description = "module.render.nametags.description", category = Category.RENDER)
public final class NameTags extends Module {

    private final BooleanValue health = new BooleanValue("Show Health", this, true);
    // Show health option doesn't work until we come up with a design that looks good without the health
    // To be honest I don't care alan

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        Font nunitoLight14 = FontManager.getNunitoLight(14);
        for (EntityLivingBase entity : Client.INSTANCE.getTargetManager()) {
            if (entity == mc.thePlayer) {
                continue;
            }

            entity.renderNameTag = false;

            Vector4d position = ProjectionComponent.get(entity);

            if (position == null) {
                continue;
            }

            final String text = entity.getCommandSenderName();
            final double nameWidth = nunitoLightNormal.width(text);

            final double posX = (position.x + (position.z - position.x) / 2);
            final double posY = position.y - 2;
            final double margin = 2;

            final int multiplier = 2;
            final double nH = nunitoLightNormal.height() + (this.health.getValue() ? nunitoLight14.height() : 0) + margin * multiplier;
            final double nY = posY - nH;

            NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), getTheme().getDropShadow());
            });

            NORMAL_RENDER_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), getTheme().getBackgroundShade());
                nunitoLightNormal.drawCenteredString(text, posX, nY + margin * 2, getTheme().getFirstColor().getRGB());

                if (this.health.getValue()) {
                    nunitoLight14.drawCenteredString(String.valueOf((int) entity.getHealth()), posX, posY + 1 + 3 - margin - FontRenderer.FONT_HEIGHT, Color.WHITE.hashCode());
                }
            });

            NORMAL_BLUR_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), Color.BLACK);
            });
        }
    };
}