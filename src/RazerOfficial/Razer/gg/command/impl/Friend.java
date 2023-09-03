package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Alan
 * @since 10/19/2022
 */
@Rise
public final class Friend extends Command {

    public Friend() {
        super("command.friend.description", "friend", "setfriend", "f");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length != 3) {
            error(".f <add/remove> <player>");
        } else {
            String action = args[1].toLowerCase();
            String target = args[2];
            Boolean success = false;
            for (EntityPlayer entityPlayer: mc.theWorld.playerEntities) {
                if (!entityPlayer.getCommandSenderName().equalsIgnoreCase(target)) {
                    continue;
                }
                switch (action) {
                    case "add":
                        Razer.INSTANCE.getBotManager().add(entityPlayer);
                        ChatUtil.display(String.format("Added %s to friends list", target));
                        success = true;
                        break;

                    case "remove":
                        Razer.INSTANCE.getBotManager().remove(entityPlayer);
                        ChatUtil.display(String.format("Removed %s from friends list", target));
                        success = true;
                        break;
                }
                break;
            }
            if(!success) {
                ChatUtil.display("That user could not be found.");
            }
        }
    }
}