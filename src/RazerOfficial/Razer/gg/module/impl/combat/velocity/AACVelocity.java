package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.value.Mode;

public final class AACVelocity extends Mode<Velocity> {

    private boolean jump;

    public AACVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (mc.thePlayer.hurtTime > 0 && !BadPacketsComponent.bad(false, true,false,false,false)) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
        }

        jump = false;
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (jump) {
            event.setJump(true);
        }
    };
}
