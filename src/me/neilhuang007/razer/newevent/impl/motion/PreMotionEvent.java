package me.neilhuang007.razer.newevent.impl.motion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;

@Getter
@Setter
@AllArgsConstructor
public final class PreMotionEvent extends CancellableEvent {
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
}