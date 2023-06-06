package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.command.Command;

/**
 * @author Alan
 * @since 3/02/2022
 */
@Rise
public final class Panic extends Command {

    public Panic() {
        super("command.panic.description", "panic", "p");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getModuleManager().getAll().stream().filter(module ->
                !module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(false));
    }
}