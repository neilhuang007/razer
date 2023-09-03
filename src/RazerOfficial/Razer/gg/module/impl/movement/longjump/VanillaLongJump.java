package RazerOfficial.Razer.gg.module.impl.movement.longjump;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.LongJump;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class VanillaLongJump extends Mode<LongJump> {

    private final NumberValue height = new NumberValue("Height", this, 0.5, 0.1, 1, 0.01);
    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public VanillaLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (InstanceAccess.mc.thePlayer.onGround) {
            InstanceAccess.mc.thePlayer.motionY = height.getValue().floatValue();
        }

        event.setSpeed(speed.getValue().floatValue());
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}