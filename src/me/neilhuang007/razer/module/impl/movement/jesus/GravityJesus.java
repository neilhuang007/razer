package me.neilhuang007.razer.module.impl.movement.jesus;

import me.neilhuang007.razer.module.impl.movement.Jesus;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.WaterEvent;
import me.neilhuang007.razer.value.Mode;

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