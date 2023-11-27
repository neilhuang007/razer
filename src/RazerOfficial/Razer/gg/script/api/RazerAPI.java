package RazerOfficial.Razer.gg.script.api;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.component.impl.render.NotificationComponent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.ScriptCommand;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.ScriptModule;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.vector.ScriptVector2;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.vector.ScriptVector3;
import RazerOfficial.Razer.gg.script.util.ScriptModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Strikeless
 * @since 11.06.2022
 */
public class RazerAPI {
    private static final Map<Module, ScriptModule> SCRIPT_MODULE_MAP = new HashMap<>();

    private static final Map<Command, ScriptCommand> SCRIPT_COMMAND_MAP = new HashMap<>();

    private static ScriptModule getModule(final Module module) {
        SCRIPT_MODULE_MAP.putIfAbsent(module, new ScriptModule(module));
        return SCRIPT_MODULE_MAP.get(module);
    }

    private static ScriptCommand getCommand(final Command command) {
        SCRIPT_COMMAND_MAP.putIfAbsent(command, new ScriptCommand(command));
        return SCRIPT_COMMAND_MAP.get(command);
    }

    public ScriptModule registerModule(final String name, final String description) {
        // Sometimes my genius is almost frightening
        final AtomicReference<ScriptModule> scriptModuleReference = new AtomicReference<>(null);

        final Module module = new Module(new ScriptModuleInfo(name, description)) {
            @Override
            protected void onEnable() {
                final ScriptModule scriptModule = scriptModuleReference.get();
                if (scriptModule == null) return;

                scriptModule.call("onEnable");
            }

            @Override
            protected void onDisable() {
                final ScriptModule scriptModule = scriptModuleReference.get();
                if (scriptModule == null) return;

                scriptModule.call("onDisable");
            }
        };

        scriptModuleReference.set(getModule(module));
        Razer.INSTANCE.getModuleManager().add(module);

        if(Razer.INSTANCE.getStandardClickGUI() != null) Razer.INSTANCE.getStandardClickGUI().rebuildModuleCache();
        return scriptModuleReference.get();
    }

    public ScriptModule[] getModules() {
        final List<Module> modules = Razer.INSTANCE.getModuleManager();
        final ScriptModule[] scriptModules = new ScriptModule[modules.size()];

        for (int i = 0; i < modules.size(); ++i) {
            scriptModules[i] = getModule(modules.get(i));
        }

        return scriptModules;
    }

    public ScriptModule getModule(final String name) {
        return getModule(Razer.INSTANCE.getModuleManager().get(name));
    }

    public ScriptCommand registerCommand(final String name, final String description) {
        // Sometimes my genius is almost frightening
        final AtomicReference<ScriptCommand> scriptCommandReference = new AtomicReference<>(null);

        final Command command = new Command(description, name) {
            @Override
            public void execute(final String[] args) {
                final ScriptCommand scriptCommand = scriptCommandReference.get();
                if (scriptCommand == null) return;

                scriptCommand.call("onExecute", (Object[]) args);
            }
        };

        scriptCommandReference.set(getCommand(command));
        Razer.INSTANCE.getCommandManager().add(command);

        return scriptCommandReference.get();
    }

    public ScriptCommand[] getCommands() {
        final List<Command> commands = Razer.INSTANCE.getCommandManager();
        final ScriptCommand[] scriptCommands = new ScriptCommand[commands.size()];

        for (int i = 0; i < commands.size(); ++i) {
            scriptCommands[i] = getCommand(commands.get(i));
        }

        return scriptCommands;
    }

    public ScriptCommand getCommand(final String name) {
        return getCommand(Razer.INSTANCE.getCommandManager().get(name));
    }

    public void displayChat(final String message) {
        ChatUtil.display(message);
    }

    public void displayInfoNotification(final String title, final String message) {
        NotificationComponent.post(title, message);
    }

    public void displayInfoNotification(final String title, final String message, final int time) {
        NotificationComponent.post(title, message, time);
    }

    public String getRiseName() {
        return Razer.NAME;
    }

    public String getRiseVersion() {
        return Razer.VERSION;
    }

    public long getSystemMillis() {
        return System.currentTimeMillis();
    }


    public ScriptVector2 newVec2(Number x, Number y) {
        return new ScriptVector2(x, y);
    }

    public ScriptVector3 newVec3(Number x, Number y, Number z) {
        return new ScriptVector3(x, y, z);
    }
}
