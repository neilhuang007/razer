package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Strikeless
 * @since 03.07.2022
 */
public class VulcanFlight extends Mode<Flight> {

    private int ticks;

    public VulcanFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ticks = 0;
        PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ,
                mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final float speed = 1;

        mc.thePlayer.motionY = -1E-10D
                + (mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);

        if (mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speed - 0.15) {
            event.setCancelled(true);
        } else {
            ticks++;

            if (ticks >= 8) {
                MoveUtil.stop();
                getParent().toggle();
            }
        }
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = 1;

        event.setSpeed(speed);
    };
}
