package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class MoveEvent extends CancellableEvent {

    private double posX, posY, posZ;
}
