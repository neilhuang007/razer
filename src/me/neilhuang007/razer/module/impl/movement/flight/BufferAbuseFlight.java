package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.component.impl.player.BadPacketsComponent;
import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class BufferAbuseFlight extends Mode<Flight> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);
    private final BooleanValue sendFlying = new BooleanValue("Send Flying", this, false);

    public BufferAbuseFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = this.speed.getValue().floatValue();
        event.setSpeed(speed);
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final float speed = this.speed.getValue().floatValue();

        mc.thePlayer.motionY = -1E-10D
                + (mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);

        if (mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speed - 0.15) {
            event.setCancelled(true);
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {

        event.setSneak(false);
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        if (!sendFlying.getValue()) {
            Packet<?> packet = event.getPacket();

            if (packet instanceof C03PacketPlayer) {
                C03PacketPlayer c03PacketPlayer = ((C03PacketPlayer) packet);

                if (!c03PacketPlayer.isMoving() && !BadPacketsComponent.bad()) {
                    event.setCancelled(true);
                }
            }
        }
    };
}