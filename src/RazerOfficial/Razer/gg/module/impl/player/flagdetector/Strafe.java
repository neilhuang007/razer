package RazerOfficial.Razer.gg.module.impl.player.flagdetector;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.FlagDetector;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;


public class Strafe extends Mode<FlagDetector> {

    public Strafe(String name, FlagDetector parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.offGroundTicks <= 1 || event.isOnGround() || InstanceAccess.mc.thePlayer.ticksSinceVelocity == 1 ||
                InstanceAccess.mc.thePlayer.capabilities.isFlying || InstanceAccess.mc.thePlayer.isCollidedHorizontally ||
                InstanceAccess.mc.thePlayer.ticksSinceTeleport == 1 || InstanceAccess.mc.thePlayer.ticksSincePlayerVelocity <= 20) return;

        double moveFlying = 0.02599999835384377;
        double friction = 0.9100000262260437;

        double lastDeltaX = Math.abs(InstanceAccess.mc.thePlayer.lastMotionX) * friction;
        double lastDeltaZ = Math.abs(InstanceAccess.mc.thePlayer.lastMotionZ) * friction;

        double deltaX = Math.abs(InstanceAccess.mc.thePlayer.motionX);
        double deltaZ = Math.abs(InstanceAccess.mc.thePlayer.motionZ);

        if (Math.abs(lastDeltaX - deltaX) > moveFlying || Math.abs(lastDeltaZ - deltaZ) > moveFlying) {
            getParent().fail("Strafe");
        }

    };
}
