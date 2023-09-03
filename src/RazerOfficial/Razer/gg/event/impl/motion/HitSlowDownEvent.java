package RazerOfficial.Razer.gg.event.impl.motion;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class HitSlowDownEvent extends CancellableEvent {
    public double slowDown;
    public boolean sprint;
}
