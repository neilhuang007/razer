package me.neilhuang007.razer.component.impl.viamcp;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.viamcp.ViaMCP;

public final class BlockPlacementFixComponent extends Component {

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        if (ViaMCP.getInstance().getVersion() >= ProtocolVersion.v1_11.getVersion()) {
            final Packet<?> packet = event.getPacket();

            if (packet instanceof C08PacketPlayerBlockPlacement) {
                final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

                wrapper.facingX /= 16.0F;
                wrapper.facingY /= 16.0F;
                wrapper.facingZ /= 16.0F;

                event.setPacket(wrapper);
            }
        }
    };

}
