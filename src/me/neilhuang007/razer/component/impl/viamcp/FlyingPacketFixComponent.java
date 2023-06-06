package me.neilhuang007.razer.component.impl.viamcp;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.viamcp.ViaMCP;

public final class FlyingPacketFixComponent extends Component {

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (ViaMCP.getInstance().getVersion() > ProtocolVersion.v1_8.getVersion()) {
            final Packet<?> packet = event.getPacket();

            if (packet instanceof C03PacketPlayer) {
                final C03PacketPlayer wrapper = ((C03PacketPlayer) packet);

                if (!wrapper.isMoving() && !wrapper.isRotating()) {
                    event.setCancelled(true);
                }
            }
        }
    };
}
