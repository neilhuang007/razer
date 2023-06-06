package me.neilhuang007.razer.module.impl.movement.speed;

import me.neilhuang007.razer.module.impl.movement.Speed;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;

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
