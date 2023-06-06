package me.neilhuang007.razer.script.api.wrapper.impl.event.impl;


import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

/**
 * @author Auth
 * @since 9/07/2022
 */
public class ScriptPreUpdateEvent extends ScriptEvent<PreUpdateEvent> {

    public ScriptPreUpdateEvent(final PreUpdateEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onPreUpdate";
    }
}
