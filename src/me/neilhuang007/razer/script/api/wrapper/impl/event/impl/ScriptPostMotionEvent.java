package me.neilhuang007.razer.script.api.wrapper.impl.event.impl;


import me.neilhuang007.razer.newevent.impl.motion.PostMotionEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.event.ScriptEvent;

/**
 * @author Auth
 * @since 9/07/2022
 */
public class ScriptPostMotionEvent extends ScriptEvent<PostMotionEvent> {

    public ScriptPostMotionEvent(final PostMotionEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onPostMotion";
    }
}
