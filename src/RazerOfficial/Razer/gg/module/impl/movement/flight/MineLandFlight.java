package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MathHelper;

/**
 * @author Alan
 * @since 03.07.2022
 */
public class MineLandFlight extends Mode<Flight> {

    private double serverPosX, serverPosY, serverPosZ;
    private boolean teleported;
    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (!teleported) {

            final double yaw = MoveUtil.direction();
            final double speed = 6;

            if (mc.thePlayer.ticksExisted % 3 == 0) {
                PacketUtil.send(new C03PacketPlayer(mc.thePlayer.onGround));
                mc.thePlayer.setPosition(serverPosX, serverPosY, serverPosZ);
            }

            event.setPosY(event.getPosY() - 1.1 + (mc.thePlayer.ticksExisted % 3 == 0 ? 0.42f : 0));
            event.setPosX(event.getPosX() + MathHelper.sin((float) yaw) * speed);
            event.setPosZ(event.getPosZ() - MathHelper.cos((float) yaw) * speed);

        } else {
            mc.timer.timerSpeed = 0.3f;
        }
    };
    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S08PacketPlayerPosLook && !teleported) {
            event.setCancelled(true);
        } else if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId() && wrapper.motionY / 8000D > 0.5) {
                teleported = true;
            }
        }
    };

    public MineLandFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        serverPosX = mc.thePlayer.posX;
        serverPosY = mc.thePlayer.posY;
        serverPosZ = mc.thePlayer.posZ;
        teleported = false;
    }

    @Override
    public void onDisable() {
    }
}
