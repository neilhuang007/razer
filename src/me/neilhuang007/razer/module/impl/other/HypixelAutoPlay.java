package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.render.NotificationComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.util.chat.ChatUtil;
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
