package me.neilhuang007.razer.module.impl.movement.sneak;

import me.neilhuang007.razer.module.impl.movement.Sneak;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;

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