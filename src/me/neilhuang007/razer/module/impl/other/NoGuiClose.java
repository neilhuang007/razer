package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

/**
 * @author Alan Jr.
 * @since 9/17/2022
 */
@Rise
@ModuleInfo(name = "module.other.noguiclose.name", category = Category.OTHER, description = "module.other.noguiclose.description")
public final class NoGuiClose extends Module {
    private final BooleanValue chatonly = new BooleanValue("Chat Only", this, false);

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        final Packet<?> packet = event.getPacket();
        if (event.getPacket() instanceof S2EPacketCloseWindow && (mc.currentScreen instanceof GuiChat || !chatonly.getValue())) {
            event.setCancelled(true);
        }
    };
}

