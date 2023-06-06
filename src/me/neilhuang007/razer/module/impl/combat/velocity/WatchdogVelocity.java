package me.neilhuang007.razer.module.impl.combat.velocity;

import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.TeleportEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.vector.Vector2d;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import util.time.StopWatch;

public final class WatchdogVelocity extends Mode<Velocity> {

    private StopWatch stopWatch = new StopWatch();
    private Vector2d velocity;
    private boolean ignoreTeleport;

    public WatchdogVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (ignoreTeleport) {
            ignoreTeleport = false;
            event.setCancelled(true);
            ChatUtil.display("Cancelled Velocity Teleport");
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (velocity == null) {
            stopWatch.reset();
            return;
        }

        if (stopWatch.finished(10000) && mc.thePlayer.onGround) {
            stopWatch.reset();

            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + velocity.y, mc.thePlayer.posZ, false));
            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));

            ChatUtil.display("Sent Velocity");

            ignoreTeleport = true;

            velocity = null;
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress)
            return;

        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled(true);

                velocity = new Vector2d(Math.hypot(wrapper.motionX / 8000.0D, wrapper.motionZ / 8000.0D),
                        wrapper.getMotionY() / 8000.0D);
            }
        } else if (p instanceof S27PacketExplosion) {
            event.setCancelled(true);
        }
    };
}
