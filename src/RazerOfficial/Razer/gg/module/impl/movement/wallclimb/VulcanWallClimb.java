package RazerOfficial.Razer.gg.module.impl.movement.wallclimb;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.WallClimb;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.util.MathHelper;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class VulcanWallClimb extends Mode<WallClimb> {

    public VulcanWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.ticksExisted % 2 == 0) {
                event.setOnGround(true);
                InstanceAccess.mc.thePlayer.motionY = MoveUtil.jumpMotion();
            }

            final double yaw = MoveUtil.direction();
            event.setPosX(event.getPosX() - -MathHelper.sin((float) yaw) * 0.1f);
            event.setPosZ(event.getPosZ() - MathHelper.cos((float) yaw) * 0.1f);
        }
    };
}