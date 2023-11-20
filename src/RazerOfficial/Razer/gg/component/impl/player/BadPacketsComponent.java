package RazerOfficial.Razer.gg.component.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import static net.minecraft.network.play.client.C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT;

@Razer
public final class BadPacketsComponent extends Component {

    private static boolean slot, attack, swing, block, inventory;

    public static boolean bad() {
        return bad(true, true, true, true, true);
    }

    public static boolean bad(final boolean slot, final boolean attack, final boolean swing, final boolean block, final boolean inventory) {
        return (BadPacketsComponent.slot && slot) ||
                (BadPacketsComponent.attack && attack) ||
                (BadPacketsComponent.swing && swing) ||
                (BadPacketsComponent.block && block) ||
                (BadPacketsComponent.inventory && inventory);
    }

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C09PacketHeldItemChange) {
            slot = true;
        } else if (packet instanceof C0APacketAnimation) {
            swing = true;
        } else if (packet instanceof C02PacketUseEntity) {
            attack = true;
        } else if (packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging) {
            block = true;
        } else if (packet instanceof C0EPacketClickWindow ||
                (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus) packet).getStatus() == OPEN_INVENTORY_ACHIEVEMENT) ||
                packet instanceof C0DPacketCloseWindow) {
            inventory = true;
        } else if (packet instanceof C03PacketPlayer) {
            reset();
        }
    };

    public static void reset() {
        slot = false;
        swing = false;
        attack = false;
        block = false;
        inventory = false;
    }
}
