package RazerOfficial.Razer.gg.module.impl.movement.wallclimb;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.WallClimb;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Alan
 * @since 05.06.2022
 */

public class MineMenClubWallClimb extends Mode<WallClimb> {

    private boolean hitHead;

    public MineMenClubWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally && !hitHead && InstanceAccess.mc.thePlayer.ticksExisted % 3 == 0) {
            InstanceAccess.mc.thePlayer.motionY = MoveUtil.jumpMotion();
        }

        if (InstanceAccess.mc.thePlayer.isCollidedVertically) {
            hitHead = !InstanceAccess.mc.thePlayer.onGround;
        }
    };
}