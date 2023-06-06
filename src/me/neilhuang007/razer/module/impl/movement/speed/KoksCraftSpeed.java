package me.neilhuang007.razer.module.impl.movement.speed;

import me.neilhuang007.razer.module.impl.movement.Speed;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class KoksCraftSpeed extends Mode<Speed> {

    int jumps;

    public KoksCraftSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        jumps = 0;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround) {
            if (mc.thePlayer.hurtTime == 0) MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * 0.99);

            mc.thePlayer.jump();

            jumps++;
        }

        if (mc.thePlayer.offGroundTicks == 1 && mc.thePlayer.hurtTime == 0) {
            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, jumps % 2 == 0 ? 2 : 4);
        }
    };

}
