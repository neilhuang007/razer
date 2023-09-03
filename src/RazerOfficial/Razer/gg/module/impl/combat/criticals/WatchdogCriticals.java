package RazerOfficial.Razer.gg.module.impl.combat.criticals;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Criticals;
import RazerOfficial.Razer.gg.value.Mode;

public final class WatchdogCriticals extends Mode<Criticals> {

    public WatchdogCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksSinceVelocity <= 18 && mc.thePlayer.fallDistance < 1.3) {
            event.setOnGround(false);
        }
    };
}
