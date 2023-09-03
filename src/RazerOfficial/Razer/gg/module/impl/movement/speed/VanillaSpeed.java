package RazerOfficial.Razer.gg.module.impl.movement.speed;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Speed;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class VanillaSpeed extends Mode<Speed> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public VanillaSpeed(String name, Speed parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (MoveUtil.isMoving() && InstanceAccess.mc.thePlayer.onGround) {
            InstanceAccess.mc.thePlayer.jump();
        }

        event.setSpeed(speed.getValue().floatValue());
    };

    @Override
    public void onDisable() {

    }
}