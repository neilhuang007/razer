package me.neilhuang007.razer.module.impl.player.flagdetector;

import me.neilhuang007.razer.module.impl.player.FlagDetector;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;


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
