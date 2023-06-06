package me.neilhuang007.razer.module.impl.combat.velocity;


import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

public final class GroundVelocity extends Mode<Velocity> {

    private final NumberValue delay = new NumberValue("Delay", this, 1, 0, 20, 1);

    private int ticks;

    public GroundVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (ticks == delay.getValue().intValue()) {
            mc.thePlayer.onGround = true;
        }

        ticks++;
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (ticks == delay.getValue().intValue() + 1) {
            event.setJump(false);
        }
    };
}
