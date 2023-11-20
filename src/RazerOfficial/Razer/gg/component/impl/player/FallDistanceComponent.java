package RazerOfficial.Razer.gg.component.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;

@Razer
public final class FallDistanceComponent extends Component {

    public static float distance;
    private float lastDistance;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        final float fallDistance = mc.thePlayer.fallDistance;

        if (fallDistance == 0) {
            distance = 0;
        }

        distance += fallDistance - lastDistance;
        lastDistance = fallDistance;
    };
}
