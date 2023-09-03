package RazerOfficial.Razer.gg.module.impl.combat.velocity;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

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
