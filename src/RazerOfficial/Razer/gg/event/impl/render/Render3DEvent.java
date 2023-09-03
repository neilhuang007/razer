package RazerOfficial.Razer.gg.event.impl.render;


import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Render3DEvent implements Event {

    private final float partialTicks;
}
