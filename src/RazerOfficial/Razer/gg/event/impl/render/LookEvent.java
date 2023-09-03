package RazerOfficial.Razer.gg.event.impl.render;

import RazerOfficial.Razer.gg.event.Event;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class LookEvent implements Event {
    private Vector2f rotation;
}
