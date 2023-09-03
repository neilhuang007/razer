package RazerOfficial.Razer.gg.module.impl.combat.velocity;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

public final class TickVelocity extends Mode<Velocity> {

    private final NumberValue tickVelocity = new NumberValue("Tick Velocity", this, 1, 1, 6, 1);

    public TickVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (mc.thePlayer.hurtTime == 10 - tickVelocity.getValue().intValue()) {
            MoveUtil.stop();
        }
    };
}
