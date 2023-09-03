package RazerOfficial.Razer.gg.event.impl.motion;

import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alan
 * @since 13.03.2022
 */
@Getter
@Setter
@AllArgsConstructor
public class WaterEvent implements Event {
    private boolean water;
}
