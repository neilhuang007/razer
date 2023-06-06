package me.neilhuang007.razer.newevent.impl.motion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import me.neilhuang007.razer.newevent.Event;
import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.event.impl.ScriptStrafeEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.player.MoveUtil;

@Getter
@Setter
@AllArgsConstructor
public final class StrafeEvent extends CancellableEvent implements InstanceAccess {

    private float forward;
    private float strafe;
    private float friction;
    private float yaw;

    public void setSpeed(final double speed, final double motionMultiplier) {
        setFriction((float) (getForward() != 0 && getStrafe() != 0 ? speed * 0.98F : speed));
        mc.thePlayer.motionX *= motionMultiplier;
        mc.thePlayer.motionZ *= motionMultiplier;
    }

    public void setSpeed(final double speed) {
        setFriction((float) (getForward() != 0 && getStrafe() != 0 ? speed * 0.98F : speed));
        MoveUtil.stop();
    }

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptStrafeEvent(this);
    }
}
