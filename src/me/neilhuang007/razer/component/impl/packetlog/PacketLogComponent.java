package me.neilhuang007.razer.component.impl.packetlog;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.ServerJoinEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;

@Rise
public class PacketLogComponent extends Component  {

    private int worldChanges;

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        worldChanges++;
    };

    @EventLink()
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        worldChanges = 0;
    };

    public boolean hasChangedWorlds() {
        return worldChanges > 0;
    }
}