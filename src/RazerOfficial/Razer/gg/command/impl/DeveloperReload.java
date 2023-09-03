package RazerOfficial.Razer.gg.command.impl;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.module.api.DevelopmentFeature;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;

/**
 * @author Alan
 * @since 10/19/2021
 */
@Rise
@DevelopmentFeature
public final class DeveloperReload extends Command {

    public DeveloperReload() {
        super("Reloads the client", "developerreload", "dr");
    }

    @Override
    public void execute(final String[] args) {
        Razer.INSTANCE.terminate();
        Razer.INSTANCE.initRise();
        ChatUtil.display("Reloaded Rise");
    }
}