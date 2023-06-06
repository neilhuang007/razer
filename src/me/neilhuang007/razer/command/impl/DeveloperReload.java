package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.module.api.DevelopmentFeature;
import me.neilhuang007.razer.util.chat.ChatUtil;

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
        Client.INSTANCE.terminate();
        Client.INSTANCE.initRise();
        ChatUtil.display("Reloaded Rise");
    }
}