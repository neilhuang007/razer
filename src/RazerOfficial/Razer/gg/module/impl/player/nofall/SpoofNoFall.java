package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class SpoofNoFall extends Mode<NoFall> {

    public SpoofNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        event.setOnGround(true);

    };
}