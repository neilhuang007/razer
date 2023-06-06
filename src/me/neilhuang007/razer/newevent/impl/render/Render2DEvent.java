package me.neilhuang007.razer.newevent.impl.render;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class Render2DEvent implements Event {

    private final ScaledResolution scaledResolution;
    private final float partialTicks;

}
