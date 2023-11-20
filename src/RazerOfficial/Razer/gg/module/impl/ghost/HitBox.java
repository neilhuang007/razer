package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.render.MouseOverEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import org.lwjgl.input.Mouse;

/**
 * @author Alan
 * @since 29/01/2021
 */
@Razer
@ModuleInfo(name = "module.ghost.hitbox.name", description = "module.ghost.hitbox.description", category = Category.GHOST)
public class HitBox extends Module {
    public final NumberValue expand = new NumberValue("Expand Amount", this, 0, 0, 6, 0.01);
    private final BooleanValue effectRange = new BooleanValue("Effect range", this, true);

    private int exempt;

    @EventLink()
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        if (Mouse.isButtonDown(1)) {
            exempt = 1;
        }

        if (exempt > 0) return;

        event.setExpand(this.expand.getValue().floatValue());

        if (!this.effectRange.getValue()) {
            event.setRange(event.getRange() - expand.getValue().doubleValue());
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        exempt--;
    };
}