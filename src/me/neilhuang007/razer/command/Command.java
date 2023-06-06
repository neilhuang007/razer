package me.neilhuang007.razer.command;

import lombok.Getter;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
public abstract class Command implements InstanceAccess {

    private final String description;
    private final String[] expressions;

    public Command(final String description, final String... expressions) {
        this.description = description;
        this.expressions = expressions;
    }

    public abstract void execute(String[] args);

    protected final void error() {
        ChatUtil.display("§cInvalid command arguments.");
    }

    protected final void error(String usage) {
        error();
        ChatUtil.display("Correct Usage: " + usage);
    }
}