package RazerOfficial.Razer.gg.module.impl.movement.noslow;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.SlowDownEvent;
import RazerOfficial.Razer.gg.module.impl.movement.NoSlow;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

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