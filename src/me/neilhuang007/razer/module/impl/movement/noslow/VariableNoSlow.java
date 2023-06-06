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

public class VariableNoSlow extends Mode<NoSlow> {

    private final NumberValue multiplier = new NumberValue("Multiplier", this, 0.8, 0.2, 1, 0.05);

    public VariableNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setForwardMultiplier(multiplier.getValue().floatValue());
        event.setStrafeMultiplier(multiplier.getValue().floatValue());
    };
}