package me.neilhuang007.razer.script.api;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.TickEvent;
import me.neilhuang007.razer.script.api.wrapper.impl.ScriptWorld;

/**
 * @author Strikeless
 * @since 20.06.2022
 */
public class WorldAPI extends ScriptWorld {

    public WorldAPI() {
        super(MC.theWorld);

        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (this.wrapped == null) {
            this.wrapped = MC.theWorld;
        }
    };
}
