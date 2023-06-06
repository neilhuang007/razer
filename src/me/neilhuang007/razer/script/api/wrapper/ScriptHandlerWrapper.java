package me.neilhuang007.razer.script.api.wrapper;

import jdk.nashorn.api.scripting.JSObject;
import me.neilhuang007.razer.script.util.ScriptHandler;

/**
 * @author Strikeless
 * @since 23.06.2022
 */
public abstract class ScriptHandlerWrapper<T> extends ScriptWrapper<T> {

    private final ScriptHandler handler = new ScriptHandler();

    public ScriptHandlerWrapper(final T wrapped) {
        super(wrapped);
    }

    public void handle(final String functionName, final JSObject function) {
        this.handler.handle(functionName, function);
    }

    public void unhandle(final String functionName) {
        this.handler.unhandle(functionName);
    }

    public void call(final String functionName, final Object... parameters) {
        this.handler.call(functionName, parameters);
    }
}
