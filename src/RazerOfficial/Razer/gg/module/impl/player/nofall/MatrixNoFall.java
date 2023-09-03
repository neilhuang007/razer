package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Alan
 * @since 3/02/2022
 */
public class MatrixNoFall extends Mode<NoFall> {

    public MatrixNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        float distance = FallDistanceComponent.distance;

        if (PlayerUtil.isBlockUnder()) {
            if (distance > 2) {
                MoveUtil.strafe(0.19);
            }

            if (distance > 3 && MoveUtil.speed() < 0.2) {
                event.setOnGround(true);
                distance = 0;
            }
        }

        FallDistanceComponent.distance = distance;
    };
}