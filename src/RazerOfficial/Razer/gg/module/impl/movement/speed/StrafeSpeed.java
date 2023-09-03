package RazerOfficial.Razer.gg.module.impl.movement.speed;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Speed;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

public final class StrafeSpeed extends Mode<Speed> {

    private final BooleanValue hurtBoost = new BooleanValue("Hurt Boost", this, false);
    private final NumberValue boostSpeed = new NumberValue("Boost Speed", this, 1, 0.1, 9.5, 0.1);

    public StrafeSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (!MoveUtil.isMoving()) {
            MoveUtil.stop();
            return;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        if (hurtBoost.getValue() && mc.thePlayer.hurtTime == 9) {
            MoveUtil.strafe(boostSpeed.getValue().doubleValue());
        }

        MoveUtil.strafe();
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setJump(false);
    };
}
