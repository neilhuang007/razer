package RazerOfficial.Razer.gg.module.impl.render;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.ClickEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.DragValue;
import util.type.EvictingList;

import java.awt.*;

@ModuleInfo(name = "module.render.cpscounter.name", description = "module.render.cpscounter.description", category = Category.RENDER)
public final class CPSCounter extends Module {

    private final BooleanValue showTitle = new BooleanValue("Title", this, false);
    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));

    private final Vector2f scale = new Vector2f(RenderUtil.GENERIC_SCALE, RenderUtil.GENERIC_SCALE);
    private final EvictingList<Boolean> clicks = new EvictingList<>(20);
    private boolean clicked;
    private int cps;

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        Vector2d position = this.position.position;

        final String titleString = showTitle.getValue() ? "CPS " : "";
        final String cpsString = cps + "";

        final float titleWidth = nunitoNormal.width(titleString);
        scale.x = titleWidth + nunitoNormal.width(cpsString);

        NORMAL_POST_RENDER_RUNNABLES.add(() -> {
            RenderUtil.roundedRectangle(position.x, position.y, scale.x + 6, scale.y - 1, getTheme().getRound(), getTheme().getBackgroundShade());

            this.position.setScale(new Vector2d(scale.x + 6, scale.y - 1));

            final double textX = position.x + 3.0F;
            final double textY = position.y + scale.y / 2.0F - nunitoNormal.height() / 4.0F;
            nunitoNormal.drawStringWithShadow(titleString, textX, textY, getTheme().getFirstColor().getRGB());
            nunitoNormal.drawStringWithShadow(cpsString, textX + titleWidth, textY, Color.WHITE.getRGB());
        });

        NORMAL_BLUR_RUNNABLES.add(() -> RenderUtil.roundedRectangle(position.x, position.y, scale.x + 6, scale.y - 1, getTheme().getRound(), Color.BLACK));
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> RenderUtil.roundedRectangle(position.x, position.y, scale.x + 6, scale.y - 1, getTheme().getRound() + 1, getTheme().getDropShadow()));
    };

    @EventLink()
    public final Listener<ClickEvent> onClick = event -> {
        clicked = true;
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        cps = 0;
        clicks.add(clicked);
        clicks.forEach((click) -> {
            if (click) {
                cps++;
            }
        });
        clicked = false;
    };
}