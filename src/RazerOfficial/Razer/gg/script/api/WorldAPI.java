package RazerOfficial.Razer.gg.script.api;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.script.api.wrapper.impl.ScriptWorld;

/**
 * @author Strikeless
 * @since 20.06.2022
 */
public class WorldAPI extends ScriptWorld {

    public WorldAPI() {
        super(API.MC.theWorld);

        Razer.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (this.wrapped == null) {
            this.wrapped = API.MC.theWorld;
        }
    };
}
