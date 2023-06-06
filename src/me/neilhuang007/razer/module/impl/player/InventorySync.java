package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import net.minecraft.inventory.Container;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

@Rise
@ModuleInfo(name = "module.player.inventorysync.name", description = "module.player.inventorysync.description", category = Category.PLAYER)
public class InventorySync extends Module {

    public short action;

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S32PacketConfirmTransaction) {
            final S32PacketConfirmTransaction wrapper = (S32PacketConfirmTransaction) packet;
            final Container inventory = mc.thePlayer.inventoryContainer;

            if (wrapper.getWindowId() == inventory.windowId) {
                this.action = wrapper.getActionNumber();

                if (this.action > 0 && this.action < inventory.transactionID) {
                    inventory.transactionID = (short) (this.action + 1);
                }
            }
        }
    };
}