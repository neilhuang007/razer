package me.neilhuang007.razer.module.impl.movement.speed;

import me.neilhuang007.razer.module.impl.movement.Speed;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class VanillaSpeed extends Mode<Speed> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public VanillaSpeed(String name, Speed parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }

        event.setSpeed(speed.getValue().floatValue());
    };

    @Override
    public void onDisable() {

    }
}