package RazerOfficial.Razer.gg.module.impl.player.antivoid;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.AntiVoid;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

public class CollisionAntiVoid extends Mode<AntiVoid> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);

    public CollisionAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (FallDistanceComponent.distance > distance.getValue().intValue() && !PlayerUtil.isBlockUnder() && InstanceAccess.mc.thePlayer.posY + InstanceAccess.mc.thePlayer.motionY < Math.floor(InstanceAccess.mc.thePlayer.posY)) {
            InstanceAccess.mc.thePlayer.motionY = Math.floor(InstanceAccess.mc.thePlayer.posY) - InstanceAccess.mc.thePlayer.posY;
            if (InstanceAccess.mc.thePlayer.motionY == 0) {
                InstanceAccess.mc.thePlayer.onGround = true;
                event.setOnGround(true);
            }
        }
    };
}