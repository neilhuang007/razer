package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class StepEvent implements Event {

    private double height;
}
