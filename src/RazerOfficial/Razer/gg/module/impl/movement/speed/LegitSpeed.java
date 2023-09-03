package RazerOfficial.Razer.gg.module.impl.movement.speed;

import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Speed;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;

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
