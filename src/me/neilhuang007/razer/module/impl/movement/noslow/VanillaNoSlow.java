package me.neilhuang007.razer.module.impl.movement.noslow;

import me.neilhuang007.razer.module.impl.movement.NoSlow;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.SlowDownEvent;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
public class VanillaNoSlow extends Mode<NoSlow> {

    public VanillaNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };
}