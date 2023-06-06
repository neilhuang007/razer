package me.neilhuang007.razer.component.impl.hud.dragcomponent.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Snap {
    public double position, distance;
    public Orientation orientation;
    public boolean center, right, left;
}
