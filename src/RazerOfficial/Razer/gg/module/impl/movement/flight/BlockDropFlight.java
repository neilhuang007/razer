package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PostMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.event.impl.player.TeleportEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.util.vector.Vector3d;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 31.03.2022
 */

public class BlockDropFlight extends Mode<Flight> {

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (!mc.getNetHandler().doneLoadingTerrain) return;

        Packet packet = event.getPacket();

        if (packet instanceof C03PacketPlayer) {
            event.setCancelled(true);
        }
    };
    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);
    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? speed.getValue().floatValue() : mc.gameSettings.keyBindSneak.isKeyDown() ? -speed.getValue().floatValue() : 0;

    };
    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        final float speed = this.speed.getValue().floatValue();

        event.setSpeed(speed);
    };
    private Vector3d position;
    private Vector2f rotation;
    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(position.getX(), position.getY(), position.getZ(), rotation.getX(), rotation.getY(), false));
        PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, rotation.getX(), rotation.getY(), false));
    };
    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (!mc.getNetHandler().doneLoadingTerrain) return;

        event.setCancelled(true);
        this.position = new Vector3d(event.getPosX(), event.getPosY(), event.getPosZ());
        this.rotation = new Vector2f(event.getYaw(), event.getPitch());
    };
    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        RotationComponent.setRotations(rotation, 10, MovementFix.OFF);
    };

    public BlockDropFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();

        PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(position.getX(), position.getY(), position.getZ(), rotation.getX(), rotation.getY(), false));
    }

    @Override
    public void onEnable() {
        if (mc == null || mc.thePlayer == null) return;
        this.position = new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        this.rotation = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
    }
}