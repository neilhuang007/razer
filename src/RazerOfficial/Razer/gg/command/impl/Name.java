package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public final class Name extends Command {

    public Name() {
        super("command.name.description", "name", "ign", "username", "nick", "nickname");
    }

    @Override
    public void execute(final String[] args) {
        final String name = PlayerUtil.name();

        GuiScreen.setClipboardString(name);
        ChatUtil.display("command.name.copied", name);
    }
}
