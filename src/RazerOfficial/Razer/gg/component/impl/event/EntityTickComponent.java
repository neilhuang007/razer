package RazerOfficial.Razer.gg.component.impl.event;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

@Razer
public class EntityTickComponent extends Component {

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (mc == null || mc.theWorld == null || mc.getNetHandler() == null) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            Entity entity = mc.theWorld.getEntityByID(wrapper.getEntityID());

            if (entity == null) {
                return;
            }

            entity.ticksSinceVelocity = 0;
            if (wrapper.motionY / 8000.0D > 0.1 && Math.hypot(wrapper.motionZ / 8000.0D, wrapper.motionX / 8000.0D) > 0.2) {
                entity.ticksSincePlayerVelocity = 0;
            }
        } else if (packet instanceof S08PacketPlayerPosLook && mc.getNetHandler().doneLoadingTerrain) {
            mc.thePlayer.ticksSinceTeleport = 0;
        }
    };
}
