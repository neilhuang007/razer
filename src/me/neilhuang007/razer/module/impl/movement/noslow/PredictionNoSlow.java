package me.neilhuang007.razer.module.impl.movement.noslow;

import me.neilhuang007.razer.module.impl.movement.NoSlow;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.SlowDownEvent;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

/**
 * @author Alan
 * @since 18/11/2021
 */

public class PredictionNoSlow extends Mode<NoSlow> {

    private final NumberValue amount = new NumberValue("Amount", this, 2, 2, 5, 1);

    public PredictionNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {

        if (mc.thePlayer.onGroundTicks % this.amount.getValue().intValue() != 0) {
            event.setCancelled(true);
        }
    };
}