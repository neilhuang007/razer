package RazerOfficial.Razer.gg.script.api.wrapper.impl.event.impl;


import RazerOfficial.Razer.gg.event.impl.motion.SlowDownEvent;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.event.CancellableScriptEvent;

/**
 * @author Auth
 * @since 9/07/2022
 */
public class ScriptSlowDownEvent extends CancellableScriptEvent<SlowDownEvent> {

    public ScriptSlowDownEvent(final SlowDownEvent wrappedEvent) {
        super(wrappedEvent);
    }

    public void setStrafeMultiplier(final float strafeMultiplier) {
        this.wrapped.setStrafeMultiplier(strafeMultiplier);
    }

    public void setForwardMultiplier(final float forwardMultiplier) {
        this.wrapped.setStrafeMultiplier(forwardMultiplier);
    }

    public float getStrafeMultiplier() {
        return this.wrapped.getStrafeMultiplier();
    }

    public float getForwardMultiplier() {
        return this.wrapped.getForwardMultiplier();
    }

    @Override
    public String getHandlerName() {
        return "onSlowDown";
    }
}
