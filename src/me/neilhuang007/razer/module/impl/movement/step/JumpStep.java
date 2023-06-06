package me.neilhuang007.razer.module.impl.movement.step;

import me.neilhuang007.razer.module.impl.movement.Step;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;

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