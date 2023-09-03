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