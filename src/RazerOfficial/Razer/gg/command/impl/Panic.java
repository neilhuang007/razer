package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.command.Command;

/**
 * @author Alan
 * @since 3/02/2022
 */
@Razer
public final class Panic extends Command {

    public Panic() {
        super("command.panic.description", "panic", "p");
    }

    @Override
    public void execute(final String[] args) {
        RazerOfficial.Razer.gg.Razer.INSTANCE.getModuleManager().getAll().stream().filter(module ->
                !module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(false));
    }
}