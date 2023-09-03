package RazerOfficial.Razer.gg.module.impl.movement.speed;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Speed;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;

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
