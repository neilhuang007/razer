package me.neilhuang007.razer.module.impl.combat.velocity;


import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

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
