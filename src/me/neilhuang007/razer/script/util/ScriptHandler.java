package me.neilhuang007.razer.script.util;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.ECMAException;
import me.neilhuang007.razer.util.chat.ChatUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides methods to handle calling functions in the scripts from java
 *
 * @author Strikeless
 * @since 15.05.2022
 */
public class ScriptHandler {

    private final Map<String, JSObject> functionRegistry = new HashMap<>();

    public void handle(final String functionName, final JSObject function) {
        this.functionRegistry.put(functionName, function);
    }

    public void unhandle(final String functionName) {
        this.functionRegistry.remove(functionName);
    }

    public void call(final String functionName, final Object... parameters) {
        final JSObject function = this.functionRegistry.get(functionName);
        if (function == null) return;

        try {
            function.call(this, parameters);
        } catch (final ECMAException ex) {
            ChatUtil.display(ex.toString());
        } catch (final Exception ex) {
            ex.printStackTrace();
            ChatUtil.display("A script threw an exception, stacktrace printed.");
        }
    }
}
