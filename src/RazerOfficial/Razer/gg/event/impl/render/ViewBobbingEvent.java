package RazerOfficial.Razer.gg.event.impl.render;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class ViewBobbingEvent extends CancellableEvent {

    private int time;
}
