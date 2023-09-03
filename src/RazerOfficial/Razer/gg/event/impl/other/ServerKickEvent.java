package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class ServerKickEvent implements Event {
    public List<String> message;
}