package me.neilhuang007.razer.module.impl.combat.velocity;

import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.util.MovingObjectPosition;

public final class IntaveVelocity extends Mode<Velocity> {

    private boolean attacked;

    public IntaveVelocity(String name, Velocity parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY) && mc.thePlayer.hurtTime > 0 && !attacked) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
            mc.thePlayer.setSprinting(false);
        }

        attacked = false;
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        attacked = true;
    };
}
