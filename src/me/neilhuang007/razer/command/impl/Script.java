package me.neilhuang007.razer.command.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.script.ScriptManager;
import me.neilhuang007.razer.util.chat.ChatUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Strikeless
 * @since 14.05.2022
 */
@Rise
public final class Script extends Command {

    public Script() {
        super("command.script.description", "script", "js");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length < 2) {
            error("Valid actions are load, reload, unload, disablesecurity, folder and enablesecurity");
            return;
        }

        final String action = args[1].toLowerCase(Locale.getDefault());

        final ScriptManager scriptManager = Client.INSTANCE.getScriptManager();

        final me.neilhuang007.razer.script.Script script;
        if (args.length > 2) {
            script = scriptManager.getScript(args[2]);
            if (script == null) {
                ChatUtil.display("command.script.notfound", args[2]);
                return;
            }
        } else script = null;

        try {
            switch (action) {
                case "load": {
                    if (script == null) scriptManager.loadScripts();
                    else script.load();
                    break;
                }

                case "reload": {
                    Client.INSTANCE.getScriptManager().reloadScripts();
                    break;
                }

                case "unload": {
                    if (script == null) scriptManager.unloadScripts();
                    else script.unload();
                    break;
                }

                case "disablesecurity": {
                    scriptManager.setSecurityMeasures(false);
                    ChatUtil.display("command.script.disablesecurity");
                    return;
                }

                case "enablesecurity": {
                    scriptManager.setSecurityMeasures(true);
                    ChatUtil.display("command.script.enablesecurity");
                    return;
                }

                case "folder": {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        File dirToOpen = new File(String.valueOf(ScriptManager.SCRIPT_DIRECTORY));
                        desktop.open(dirToOpen);
                        ChatUtil.display("command.script.folder");
                    } catch (IllegalArgumentException | IOException iae) {
                        ChatUtil.display("command.script.notfoundfolder");
                    }
                    return;
                }

                default: {
                    ChatUtil.display("command.script.unknownaction", "Valid actions are load, reload, unload, disablesecurity and enablesecurity");
                    return;
                }
            }

            ChatUtil.display(
                    "Successfully " + action + "ed "
                            + (script == null ? "all scripts" : "\"" + script.getName() + "\"")
                            + "."
            );
        } catch (final Exception ex) {
            ex.printStackTrace();
            ChatUtil.display("Failed to " + action + " a script. Stacktrace printed.");
        }
    }
}
