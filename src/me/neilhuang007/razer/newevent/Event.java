package me.neilhuang007.razer.newevent;

import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

public interface Event {
    public default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
