package RazerOfficial.Razer.gg.event.impl.packet;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public final class PacketReceiveEvent extends CancellableEvent {
    private Packet<?> packet;
}
