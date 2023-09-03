package RazerOfficial.Razer.gg.module.impl.movement.wallclimb;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.WallClimb;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Nicklas
 * @since 05.06.2022
 */
public class VerusWallClimb extends Mode<WallClimb> {

    public VerusWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.ticksExisted % 2 == 0) {
                InstanceAccess.mc.thePlayer.jump();
            }
        }
    };
}