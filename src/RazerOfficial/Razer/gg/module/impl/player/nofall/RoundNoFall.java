package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class RoundNoFall extends Mode<NoFall> {

    public RoundNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final double rounded = MoveUtil.roundToGround(event.getPosY());
        final float distance = FallDistanceComponent.distance;

        if (distance > 3 && Math.abs(rounded - event.getPosY()) < 0.005) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, rounded, mc.thePlayer.posZ);
            event.setOnGround(true);
            event.setPosY(rounded);
        }
    };
}