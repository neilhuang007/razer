package RazerOfficial.Razer.gg.module.impl.movement.flight.deprecated;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.player.DamageUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;

public class ACRFlight extends Mode<Flight> {

    private int offGroundTicks;
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

    public ACRFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
    }

}
