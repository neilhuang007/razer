package RazerOfficial.Razer.gg.component.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.util.animation.Animation;
import RazerOfficial.Razer.gg.util.animation.Easing;
import RazerOfficial.Razer.gg.util.font.Font;
import RazerOfficial.Razer.gg.util.font.FontManager;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.tuples.Triple;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import net.minecraft.client.renderer.GlStateManager;
import util.time.StopWatch;
import util.type.EvictingList;

import java.awt.*;

@Rise
public class NotificationComponent extends Component {

    private static EvictingList<Triple<String, String, Integer>> queue = new EvictingList<>(5);
    private static StopWatch time = new StopWatch();
    private static Triple<String, String, Integer> current;
    private static Animation animation = new Animation(Easing.EASE_OUT_ELASTIC, 500);

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2DEvent = event -> {
        if (current == null) return;

        Vector2d SCALE = new Vector2d(140, 30);
        Vector2d ICON_SCALE = new Vector2d(20, 20);
        Vector2d POSITION = new Vector2d(5, 27);

        double SPACER = (SCALE.y - ICON_SCALE.y) / 2f;

        boolean out = time.finished(current.getThird());

        animation.run(out ? 1.1 : 1);
        animation.setDuration(900);
        animation.setEasing(Easing.EASE_OUT_EXPO);
        double scale = animation.getValue();
        double opacity = 1 - 10 * Math.abs(1 - animation.getValue());

        if (animation.isFinished() && out) return;

        NORMAL_RENDER_RUNNABLES.add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((POSITION.x + SCALE.x / 2) * (1 - scale), (POSITION.y + SCALE.y / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(POSITION.x, POSITION.y, SCALE.x, SCALE.y, 13, ColorUtil.withAlpha(getTheme().getBackgroundShade(), (int) (getTheme().getBackgroundShade().getAlpha() * opacity)));

            RenderUtil.roundedRectangle(POSITION.x + SPACER, POSITION.y + SPACER, ICON_SCALE.x, ICON_SCALE.y, 8, ColorUtil.withAlpha(Color.WHITE, (int) (255 * opacity)));

            Font bold = FontManager.getNunitoBold(15);
            Font light = FontManager.getNunito(15);
            bold.drawStringWithShadow(current.getFirst(), POSITION.x + SPACER + ICON_SCALE.x + SPACER, POSITION.y + SPACER + 3, ColorUtil.withAlpha(getTheme().getFirstColor(), (int) (255 * opacity)).getRGB());

            light.drawStringWithShadow(current.getSecond(), POSITION.x + SPACER + ICON_SCALE.x + SPACER, POSITION.y + SPACER + 0.5 + SPACER * 0.7 + bold.height(), ColorUtil.withAlpha(Color.WHITE, (int) (255 * opacity)).getRGB());

            GlStateManager.popMatrix();
        });

        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((POSITION.x + SCALE.x / 2) * (1 - scale), (POSITION.y + SCALE.y / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(POSITION.x, POSITION.y, SCALE.x, SCALE.y, 13, ColorUtil.withAlpha(getTheme().getBackgroundShade(), (int) (getTheme().getBackgroundShade().getAlpha() * opacity)));

            GlStateManager.popMatrix();
        });

        NORMAL_BLUR_RUNNABLES.add(() -> {
            if (Math.abs(animation.getValue() - 1) > 0.045) return;

            GlStateManager.pushMatrix();
            GlStateManager.translate((POSITION.x + SCALE.x / 2) * (1 - scale), (POSITION.y + SCALE.y / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(POSITION.x, POSITION.y, SCALE.x, SCALE.y, 13, Color.BLACK);

            GlStateManager.popMatrix();
        });

    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mc.thePlayer.ticksExisted % 5 != 0) return;

        if (!queue.isEmpty() && (current == null || time.finished(current.getThird() + 200))) {
            if (current != null) queue.remove(current);
            current = queue.get(0);
            time.reset();
        }
    };

    public static void post(String title, String description) {
        post(title, description, 3000);
    }

    public static void post(String title, String description, Integer time) {
        queue.add(new Triple<>(title, description, time));
    }

}
