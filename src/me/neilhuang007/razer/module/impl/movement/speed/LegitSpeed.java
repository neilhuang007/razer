package me.neilhuang007.razer.module.impl.movement.speed;

import me.neilhuang007.razer.component.impl.player.RotationComponent;
import me.neilhuang007.razer.component.impl.player.rotationcomponent.MovementFix;
import me.neilhuang007.razer.module.impl.movement.Speed;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.util.vector.Vector2f;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.SubMode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class LegitSpeed extends Mode<Speed> {

    private ModeValue rotationExploit = new ModeValue("Rotation Exploit Mode", this)
            .add(new SubMode("Off"))
            .add(new SubMode("Rotate (Fully Legit)"))
            .add(new SubMode("Speed Equivalent (Almost legit, Very hard to flag)"))
            .setDefault("Speed Equivalent (Almost legit, Very hard to flag)");
    private BooleanValue cpuSpeedUpExploit = new BooleanValue("CPU SpeedUp Exploit", this, true);
    private BooleanValue noJumpDelay = new BooleanValue("No Jump Delay", this, true);

    public LegitSpeed(String name, Speed parent) {
        super(name, parent);
    }


    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> preUpdate = event -> {
        switch (rotationExploit.getValue().getName()) {
            case "Rotate (Fully Legit)":
                if (!mc.thePlayer.onGround)
                    RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw + 45, mc.thePlayer.rotationPitch), 10, MovementFix.NORMAL);
                break;

            case "Speed Equivalent (Almost legit, Very hard to flag)":
                MoveUtil.useDiagonalSpeed();
                break;
        }

        if (noJumpDelay.getValue()) {
            mc.thePlayer.jumpTicks = 0;
        }

        if (cpuSpeedUpExploit.getValue()) {
            mc.timer.timerSpeed = 1.004f;
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<StrafeEvent> strafe = event -> {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}
