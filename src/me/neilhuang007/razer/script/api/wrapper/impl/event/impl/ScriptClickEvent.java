package me.neilhuang007.razer.script.api.wrapper.impl.event.impl;


import me.neilhuang007.razer.newevent.impl.input.ClickEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

/**
 * @author Auth
 * @since 9/07/2022
 */
public class ScriptClickEvent extends ScriptEvent<ClickEvent> {

    public ScriptClickEvent(final ClickEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onClick";
    }
}
