package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.localization.Localization;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public final class Toggle extends Command {

    public Toggle() {
        super("command.toggle.description", "toggle", "t");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length != 2) {
            error(String.format(".%s <module>", args[0]));
            return;
        }
        final Module module = Client.INSTANCE.getModuleManager().get(args[1]);
        if (module == null) {
            ChatUtil.display(Localization.get("command.bind.invalidmodule"));
            return;
        }
        module.toggle();
        ChatUtil.display(
                Localization.get("command.toggle.toggled"),
                Localization.get(module.getModuleInfo().name()) + " " + (module.isEnabled() ?  EnumChatFormatting.GREEN + "on": EnumChatFormatting.RED + "off")
        );
    }
}