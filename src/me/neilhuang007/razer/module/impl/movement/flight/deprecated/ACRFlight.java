package me.neilhuang007.razer.module.impl.movement.flight.deprecated;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.DamageUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;

public class ACRFlight extends Mode<Flight> {

    private int offGroundTicks;

    public ACRFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.strafe(0.06);
        if (mc.thePlayer.ticksExisted % 3 == 0) {
            mc.thePlayer.motionY = 0.40444491418477213;
            offGroundTicks = 0;
        }

        if (offGroundTicks == 1) {
            MoveUtil.strafe(0.36);
            mc.thePlayer.motionY = 0.33319999363422365;
        }
    };

}
