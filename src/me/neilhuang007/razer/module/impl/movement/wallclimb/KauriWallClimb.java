package me.neilhuang007.razer.module.impl.movement.wallclimb;

import me.neilhuang007.razer.module.impl.movement.WallClimb;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Nicklas
 * @since 05.06.2022
 */

public class KauriWallClimb extends Mode<WallClimb> {

    public KauriWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.ticksExisted % 3 == 0) {
                event.setOnGround(true);
                InstanceAccess.mc.thePlayer.jump();
            }
        }
    };
}