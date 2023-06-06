package me.neilhuang007.razer.module.impl.movement.speed;

import me.neilhuang007.razer.module.impl.movement.Speed;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class MineMenClubSpeed extends Mode<Speed> {

    public MineMenClubSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (mc.thePlayer.hurtTime <= 6) {
            MoveUtil.strafe();
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}