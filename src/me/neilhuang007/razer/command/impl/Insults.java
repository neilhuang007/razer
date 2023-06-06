package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.util.chat.ChatUtil;

@Rise
public final class Insults extends Command {

    public Insults() {
        super("command.insults.description", "insults", "killinsults", "insult");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length >= 3) {
            final String request = args[1].toLowerCase();
            final String name = args[2];

            if (request.equals("create")) {
                instance.getInsultManager().set(name);

                ChatUtil.display("command.insults.created", name);
            } else if (request.equals("delete")) {
                instance.getInsultManager().delete(name);

                ChatUtil.display("command.insults.removed", name);
            }
        } else {
            ChatUtil.display("command.insults.help1");
            ChatUtil.display("command.insults.help2");
        }
    }
}
