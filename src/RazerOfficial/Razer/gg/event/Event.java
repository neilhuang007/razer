package RazerOfficial.Razer.gg.event;

import RazerOfficial.Razer.gg.script.api.wrapper.impl.event.ScriptEvent;

public interface Event {
    public default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
