package RazerOfficial.Razer.gg.module.impl.player.flagdetector;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.FlagDetector;
import RazerOfficial.Razer.gg.value.Mode;


public class MathGround extends Mode<FlagDetector> {

    public MathGround(String name, FlagDetector parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (event.isOnGround() && event.getPosY() % (1 / 64f) != 0) {
            getParent().fail("Math Ground");
        }

    };
}
