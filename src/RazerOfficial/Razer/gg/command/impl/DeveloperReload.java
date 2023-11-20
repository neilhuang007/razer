package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.module.api.DevelopmentFeature;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;

/**
 * @author Alan
 * @since 10/19/2021
 */
@Razer
@DevelopmentFeature
public final class DeveloperReload extends Command {

    public DeveloperReload() {
        super("Reloads the client", "developerreload", "dr");
    }

    @Override
    public void execute(final String[] args) {
        RazerOfficial.Razer.gg.Razer.INSTANCE.terminate();
        RazerOfficial.Razer.gg.Razer.INSTANCE.initRise();
        ChatUtil.display("Reloaded Razer");
    }
}