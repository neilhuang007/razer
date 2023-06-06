package me.neilhuang007.razer.script.api.wrapper.impl.event.impl;


import me.neilhuang007.razer.newevent.impl.input.ChatInputEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.event.CancellableScriptEvent;

/**
 * @author Auth
 * @since 9/07/2022
 */
public class ScriptChatInputEvent extends CancellableScriptEvent<ChatInputEvent> {

    public ScriptChatInputEvent(final ChatInputEvent wrappedEvent) {
        super(wrappedEvent);
    }

    public String getMessage() {
        return this.wrapped.getMessage();
    }

    @Override
    public String getHandlerName() {
        return "onChatInput";
    }
}
