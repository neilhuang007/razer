package RazerOfficial.Razer.gg.component.impl.packetlog;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.ServerJoinEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;

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