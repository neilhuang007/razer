package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.render.NotificationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

@Rise
@ModuleInfo(name = "module.other.hypixelautoplay.name", description = "module.other.autogg.description", category = Category.OTHER)
public final class HypixelAutoPlay extends Module {

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat chat = ((S02PacketChat) packet);

            if (chat.getChatComponent().getFormattedText().contains("play again?")) {
                for (IChatComponent iChatComponent : chat.getChatComponent().getSiblings()) {
                    for (String value : iChatComponent.toString().split("'")) {
                        if (value.startsWith("/play") && !value.contains(".")) {
                            ChatUtil.send(value);
                            NotificationComponent.post("Auto Play", "Joined a new game", 7000);
                            break;
                        }
                    }
                }
            }
        }
    };

}
