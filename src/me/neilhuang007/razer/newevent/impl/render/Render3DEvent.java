package me.neilhuang007.razer.newevent.impl.render;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;

@Getter
@AllArgsConstructor
public final class Render3DEvent implements Event {

    private final float partialTicks;
}
