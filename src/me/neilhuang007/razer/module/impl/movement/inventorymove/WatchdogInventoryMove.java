package me.neilhuang007.razer.module.impl.movement.inventorymove;

import me.neilhuang007.razer.component.impl.player.BadPacketsComponent;
import me.neilhuang007.razer.module.impl.movement.InventoryMove;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class WatchdogInventoryMove extends Mode<InventoryMove> {

    private boolean inventoryOpen;

    public WatchdogInventoryMove(String name, InventoryMove parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C03PacketPlayer && inventoryOpen && !(mc.currentScreen instanceof GuiChest)) {
            if (!BadPacketsComponent.bad(false, false, false, false, true))
                PacketUtil.send(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        } else if (packet instanceof C16PacketClientStatus) {
            final C16PacketClientStatus wrapper = (C16PacketClientStatus) packet;

            if (wrapper.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                inventoryOpen = true;
            }
        } else if (packet instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction wrapper = (C0BPacketEntityAction) packet;

            if (wrapper.getAction() == C0BPacketEntityAction.Action.OPEN_INVENTORY) {
                inventoryOpen = true;
            }
        } else if (packet instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = ((C08PacketPlayerBlockPlacement) packet);

            if (PlayerUtil.block(c08PacketPlayerBlockPlacement.getPosition()) instanceof BlockChest) {
                inventoryOpen = true;
            }
        } else if (packet instanceof C0DPacketCloseWindow) {
            inventoryOpen = false;
        } else if (packet instanceof C0EPacketClickWindow) {
            inventoryOpen = true;
        }
    };

    private final KeyBinding[] AFFECTED_BINDINGS = new KeyBinding[]{
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindJump
    };

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiChest || mc.currentScreen == this.getStandardClickGUI()) {
            return;
        }

        for (final KeyBinding bind : AFFECTED_BINDINGS) {
            bind.setPressed(GameSettings.isKeyDown(bind));
        }
    };
}