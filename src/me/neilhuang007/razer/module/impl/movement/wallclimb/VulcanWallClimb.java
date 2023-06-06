package me.neilhuang007.razer.module.impl.movement.wallclimb;

import me.neilhuang007.razer.module.impl.movement.WallClimb;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
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