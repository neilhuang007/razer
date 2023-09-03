package RazerOfficial.Razer.gg.event.impl.render;


import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class RenderHungerEvent implements Event {

    private final ScaledResolution scaledResolution;

}
