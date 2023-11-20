package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;

/**
 * @author Auth
 * @since 3/02/2022
 */
@Razer
public final class Say extends Command {

    public Say() {
        super("command.say.description", "say", "chat");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length <= 1) {
            error(String.format(".%s <message>", args[0]));
        } else {
            ChatUtil.send(String.join(" ", args).substring(3).trim());
            ChatUtil.display("command.say.sent");
        }
    }
}
