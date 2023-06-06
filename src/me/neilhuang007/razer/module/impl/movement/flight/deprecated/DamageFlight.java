package me.neilhuang007.razer.module.impl.movement.flight.deprecated;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.player.DamageUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;

public class DamageFlight extends Mode<Flight> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);

    public DamageFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = this.speed.getValue().floatValue();

        event.setSpeed(speed);
    };


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        final float speed = this.speed.getValue().floatValue();

        InstanceAccess.mc.thePlayer.motionY = 0.0D
                + (InstanceAccess.mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (InstanceAccess.mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

}
