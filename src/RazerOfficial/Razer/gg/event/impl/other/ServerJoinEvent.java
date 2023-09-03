package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ServerJoinEvent implements Event {

    public String ip;
    public int port;
}