package me.neilhuang007.razer.module.impl.player.antivoid;

import me.neilhuang007.razer.component.impl.player.FallDistanceComponent;
import me.neilhuang007.razer.module.impl.player.AntiVoid;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

public class CollisionAntiVoid extends Mode<AntiVoid> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    public CollisionAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (FallDistanceComponent.distance > distance.getValue().intValue() && !PlayerUtil.isBlockUnder() && mc.thePlayer.posY + mc.thePlayer.motionY < Math.floor(mc.thePlayer.posY)) {
            mc.thePlayer.motionY = Math.floor(mc.thePlayer.posY) - mc.thePlayer.posY;
            if (mc.thePlayer.motionY == 0) {
                mc.thePlayer.onGround = true;
                event.setOnGround(true);
            }
        }
    };
}