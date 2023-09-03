package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.localization.Localization;
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
        Razer.INSTANCE.getCommandManager()
                .forEach(command -> ChatUtil.display(StringUtils.capitalize(command.getExpressions()[0]) + " " + Arrays.toString(command.getExpressions()) + " \2478» \2477" + Localization.get(command.getDescription())));
    }
}