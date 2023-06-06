package me.neilhuang007.razer.module.impl.movement.flight;


import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class OldNCPFlight extends Mode<Flight> {

    public OldNCPFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setPosY(event.getPosY() + (mc.thePlayer.ticksExisted % 2 == 0 ? RandomUtils.nextDouble(1E-10, 1E-5) : -RandomUtils.nextDouble(1E-10, 1E-5)));
        mc.thePlayer.motionY = 0;
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        event.setSpeed(MoveUtil.getAllowedHorizontalDistance(), Math.random() / 2000);
    };
}