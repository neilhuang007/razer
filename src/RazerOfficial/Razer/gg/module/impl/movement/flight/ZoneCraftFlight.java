package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.render.NotificationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.util.Vec3;

public class ZoneCraftFlight extends Mode<Flight> {

    public ZoneCraftFlight(String name, Flight parent) {
        super(name, parent);
    }

    public Vec3 position = new Vec3(0, 0, 0);

    @Override
    public void onEnable() {
        if (!mc.thePlayer.onGround) {
            toggle();
        }

        if (!Razer.DEVELOPMENT_SWITCH) {
            NotificationComponent.post("Flight", "This feature is only enabled for developers atm");
        }

        position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setPosX(position.xCoord);
        event.setPosY(position.yCoord);
        event.setPosZ(position.zCoord);
        event.setOnGround(true);
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        final float speed = 3;

        event.setSpeed(speed);

        mc.thePlayer.motionY = 0.0D;
    };

}
