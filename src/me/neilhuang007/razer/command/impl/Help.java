package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.localization.Localization;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author Auth
 * @since 3/02/2022
 */
public final class Help extends Command {

    public Help() {
        super("command.help.description", "help", "?");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getCommandManager()
                .forEach(command -> ChatUtil.display(StringUtils.capitalize(command.getExpressions()[0]) + " " + Arrays.toString(command.getExpressions()) + " \2478» \2477" + Localization.get(command.getDescription())));
    }
}