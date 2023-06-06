package me.neilhuang007.razer.module.impl.player.antivoid;

import me.neilhuang007.razer.module.impl.player.AntiVoid;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.TeleportEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

/**
 * @author Strikeless
 * @since 18.03.2022
 */
public class VulcanAntiVoid extends Mode<AntiVoid> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    private boolean teleported;

    public VulcanAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue()) {
            event.setPosY(event.getPosY() - event.getPosY() % 0.015625);
            event.setOnGround(true);

            mc.thePlayer.motionY = -0.08D;

            MoveUtil.stop();
        }

        if (teleported) {
            MoveUtil.stop();
            teleported = false;
        }
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {

        if (mc.thePlayer.fallDistance > distance.getValue().floatValue()) {
            teleported = true;
        }
    };
}
