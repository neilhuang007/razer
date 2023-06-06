package me.neilhuang007.razer.newevent.impl.input;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.Event;

@Getter
@Setter
@AllArgsConstructor
public class MoveInputEvent implements Event {
    private float forward, strafe;
    private boolean jump, sneak;
    private double sneakSlowDownMultiplier;
}
