package RazerOfficial.Razer.gg.module.impl.movement.step;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Step;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class JumpStep extends Mode<Step> {

    public JumpStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.jump();
        }
    };
}