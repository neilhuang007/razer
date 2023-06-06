package me.neilhuang007.razer.script.api.wrapper.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.script.api.wrapper.ScriptHandlerWrapper;

/**
 * @author Strikeless
 * @since 15.05.2022
 */
public final class ScriptCommand extends ScriptHandlerWrapper<Command> {

    public ScriptCommand(final Command wrapped) {
        super(wrapped);
    }

    public void unregister() {
        Client.INSTANCE.getCommandManager().remove(this.wrapped);
    }

    // TODO: Make command execution again

    public String getName() {
        return this.wrapped.getExpressions()[0];
    }

    public String getDescription() {
        return this.wrapped.getDescription();
    }
}
