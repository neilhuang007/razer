package me.neilhuang007.razer.module.impl.combat.criticals;

import me.neilhuang007.razer.module.impl.combat.Criticals;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;

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
