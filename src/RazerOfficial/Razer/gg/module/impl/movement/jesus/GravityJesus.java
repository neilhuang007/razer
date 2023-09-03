package RazerOfficial.Razer.gg.module.impl.movement.jesus;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.WaterEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Jesus;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class GravityJesus extends Mode<Jesus> {

    public GravityJesus(String name, Jesus parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<WaterEvent> onWater = event -> {
        event.setWater(event.isWater() && mc.thePlayer.offGroundTicks > 5 && mc.gameSettings.keyBindJump.isKeyDown());
    };
}