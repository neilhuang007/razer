package RazerOfficial.Razer.gg.event.impl.input;


import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveInputEvent implements Event {
    public static boolean isJump;
    public static boolean isSneak;
    private float forward, strafe;
    private boolean jump, sneak;
    private double sneakSlowDownMultiplier;
}
