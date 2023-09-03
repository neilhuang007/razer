package RazerOfficial.Razer.gg.command;

import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import lombok.Getter;

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