package me.neilhuang007.razer.module.impl.player.nofall;

import me.neilhuang007.razer.module.impl.player.NoFall;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class SpoofNoFall extends Mode<NoFall> {

    public SpoofNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        event.setOnGround(true);

    };
}