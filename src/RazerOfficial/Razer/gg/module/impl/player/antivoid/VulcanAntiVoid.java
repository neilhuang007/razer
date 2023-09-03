package RazerOfficial.Razer.gg.module.impl.player.antivoid;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.TeleportEvent;
import RazerOfficial.Razer.gg.module.impl.player.AntiVoid;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

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
