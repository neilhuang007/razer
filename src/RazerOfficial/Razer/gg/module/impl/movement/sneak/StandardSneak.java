package RazerOfficial.Razer.gg.module.impl.movement.sneak;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Sneak;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class StandardSneak extends Mode<Sneak> {

    public StandardSneak(String name, Sneak parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;
    };
}