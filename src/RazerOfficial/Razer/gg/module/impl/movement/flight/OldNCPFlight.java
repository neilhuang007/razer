package RazerOfficial.Razer.gg.module.impl.movement.flight;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
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