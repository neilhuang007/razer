package RazerOfficial.Razer.gg.event.impl.render;

import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class LimitedRender2DEvent implements Event {

    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
