package me.neilhuang007.razer.newevent.impl.motion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
@Getter
@Setter
@AllArgsConstructor
public class SlowDownEvent extends CancellableEvent {
    private float strafeMultiplier;
    private float forwardMultiplier;
}
