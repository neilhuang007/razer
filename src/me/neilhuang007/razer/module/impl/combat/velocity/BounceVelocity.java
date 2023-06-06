package me.neilhuang007.razer.module.impl.combat.velocity;


import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;

public final class BounceVelocity extends Mode<Velocity> {

    private final NumberValue tick = new NumberValue("Tick", this, 0, 0, 6, 1);
    private final BooleanValue vertical = new BooleanValue("Vertical", this, false);
    private final BooleanValue horizontal = new BooleanValue("Horizontal", this, false);

    public BounceVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (mc.thePlayer.hurtTime == 9 - this.tick.getValue().intValue()) {
            if (this.horizontal.getValue()) {
                if (MoveUtil.isMoving()) {
                    MoveUtil.strafe();
                } else {
                    mc.thePlayer.motionZ *= -1;
                    mc.thePlayer.motionX *= -1;
                }
            }

            if (this.vertical.getValue()) {
                mc.thePlayer.motionY *= -1;
            }
        }
    };
}
