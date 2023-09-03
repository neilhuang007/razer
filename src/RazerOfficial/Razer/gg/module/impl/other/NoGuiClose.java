package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
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

