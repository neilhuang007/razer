package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.render.ProjectionComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.font.Font;
import RazerOfficial.Razer.gg.util.font.FontManager;
import RazerOfficial.Razer.gg.util.font.impl.minecraft.FontRenderer;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector4d;
import java.awt.*;

/**
 * @author Alan
 * @since 29/04/2022
 */
@Razer
@ModuleInfo(name = "module.render.nametags.name", description = "module.render.nametags.description", category = Category.RENDER)
public final class NameTags extends Module {

    private final BooleanValue health = new BooleanValue("Show Health", this, true);
    // Show health option doesn't work until we come up with a design that looks good without the health
    // To be honest I don't care alan

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        Font nunitoLight14 = FontManager.getNunitoLight(14);
        for (EntityLivingBase entity : RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager()) {
            if (entity == InstanceAccess.mc.thePlayer) {
                continue;
            }

            entity.renderNameTag = false;

            Vector4d position = ProjectionComponent.get(entity);

            if (position == null) {
                continue;
            }

            final String text = entity.getCommandSenderName();
            final double nameWidth = InstanceAccess.nunitoLightNormal.width(text);

            final double posX = (position.x + (position.z - position.x) / 2);
            final double posY = position.y - 2;
            final double margin = 2;

            final int multiplier = 2;
            final double nH = InstanceAccess.nunitoLightNormal.height() + (this.health.getValue() ? nunitoLight14.height() : 0) + margin * multiplier;
            final double nY = posY - nH;

            InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), getTheme().getDropShadow());
            });

            InstanceAccess.NORMAL_RENDER_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), getTheme().getBackgroundShade());
                InstanceAccess.nunitoLightNormal.drawCenteredString(text, posX, nY + margin * 2, getTheme().getFirstColor().getRGB());

                if (this.health.getValue()) {
                    nunitoLight14.drawCenteredString(String.valueOf((int) entity.getHealth()), posX, posY + 1 + 3 - margin - FontRenderer.FONT_HEIGHT, Color.WHITE.hashCode());
                }
            });

            InstanceAccess.NORMAL_BLUR_RUNNABLES.add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), Color.BLACK);
            });
        }
    };
}