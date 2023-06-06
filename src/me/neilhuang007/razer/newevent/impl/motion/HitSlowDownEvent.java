package me.neilhuang007.razer.newevent.impl.motion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;

@Getter
@Setter
@AllArgsConstructor
public final class HitSlowDownEvent extends CancellableEvent {
    public double slowDown;
    public boolean sprint;
}
