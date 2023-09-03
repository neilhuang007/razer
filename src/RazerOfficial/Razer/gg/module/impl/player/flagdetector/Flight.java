package RazerOfficial.Razer.gg.module.impl.player.flagdetector;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.FlagDetector;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;


public class Flight extends Mode<FlagDetector> {

    public Flight(String name, FlagDetector parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.offGroundTicks <= 1 || mc.thePlayer.ticksSinceVelocity == 1 ||
                mc.thePlayer.isCollidedHorizontally || mc.thePlayer.capabilities.isFlying ||
                Math.abs(mc.thePlayer.motionY - MoveUtil.UNLOADED_CHUNK_MOTION) < 1E-5 || mc.thePlayer.isCollidedVertically ||
                mc.thePlayer.ticksSinceTeleport == 1 || mc.thePlayer.isInWeb) return;

        if (Math.abs((((Math.abs(mc.thePlayer.lastMotionY) < 0.005 ? 0 : mc.thePlayer.lastMotionY) - 0.08) * 0.98F) -
                mc.thePlayer.motionY) > 1E-5) {
            getParent().fail("Flight");
        }

    };
}
