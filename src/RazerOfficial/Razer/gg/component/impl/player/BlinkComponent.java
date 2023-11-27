package RazerOfficial.Razer.gg.component.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.ServerJoinEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import util.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

@Razer
public final class BlinkComponent extends Component {

    public static final ConcurrentLinkedQueue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
    public static boolean blinking, dispatch;
    public static ArrayList<Class<?>> exemptedPackets = new ArrayList<>();
    public static StopWatch exemptionWatch = new StopWatch();

    private static Entity blinkEntity;

    public static void setExempt(Class<?>... packets) {
        exemptedPackets = new ArrayList<>(Arrays.asList(packets));
        exemptionWatch.reset();
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (mc.thePlayer == null) {
            packets.clear();
            exemptedPackets.clear();
            return;
        }

        if (mc.thePlayer.isDead || mc.isSingleplayer() || !mc.getNetHandler().doneLoadingTerrain) {
            packets.forEach(PacketUtil::sendNoEvent);
            packets.clear();
            blinking = false;
            exemptedPackets.clear();
            return;
        }

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C00Handshake || packet instanceof C00PacketLoginStart ||
                packet instanceof C00PacketServerQuery || packet instanceof C01PacketPing ||
                packet instanceof C01PacketEncryptionResponse) {
            return;
        }

        if (blinking && !dispatch) {
            if (exemptionWatch.finished(100)) {
                exemptionWatch.reset();
                exemptedPackets.clear();
            }

            PingSpoofComponent.spoofing = false;

            if (!event.isCancelled() && exemptedPackets.stream().noneMatch(packetClass ->
                    packetClass == packet.getClass())) {
                packets.add(packet);
                event.setCancelled(true);
            }
        } else if (packet instanceof C03PacketPlayer) {
            packets.forEach(PacketUtil::sendNoEvent);
            packets.clear();
            dispatch = false;
        }
    };

    public static void dispatch() {
        dispatch = true;
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        packets.clear();
        BlinkComponent.blinking = false;
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        packets.clear();
        BlinkComponent.blinking = false;
    };


    public static void deSpawnEntity() {
        if (blinkEntity != null) {
            RazerOfficial.Razer.gg.Razer.INSTANCE.getBotManager().remove(blinkEntity);
            mc.theWorld.removeEntityFromWorld(blinkEntity.getEntityId());
            blinkEntity = null;
        }
    }

    public static void spawnEntity(Entity e) {
        if (blinkEntity == null) {
            blinkEntity = e;
            blinkEntity.setPositionAndRotation(e.posX, e.posY, e.posZ, e.rotationYaw, e.rotationPitch);
            //blinkEntity.rotationYawHead = e.rotationYawHead;
            blinkEntity.setSprinting(e.isSprinting());
            blinkEntity.setInvisible(e.isInvisible());
            blinkEntity.setSneaking(e.isSneaking());
            //blinkEntity.inventory = e.inventory;
            RazerOfficial.Razer.gg.Razer.INSTANCE.getBotManager().add(blinkEntity);

            mc.theWorld.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        }
    }

}
