package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 3/07/2022
 */
@Razer
public final class Stuck extends Command {

    public Stuck() {
        super("command.stuck.description", "stuck");
    }

    @Override
    public void execute(final String[] args) {
        PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, -1, mc.thePlayer.posZ, false));
        ChatUtil.display("command.stuck.sent");
    }
}