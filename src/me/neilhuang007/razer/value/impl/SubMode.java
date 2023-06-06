package me.neilhuang007.razer.value.impl;

import me.neilhuang007.razer.value.Mode;

/**
 * A mode inside of a mode (inside of a module)
 * EX: Speed (module) -> Verus (mode) -> LowHop -> (Submode)
 * @author Hazsi
 */
@SuppressWarnings("all")
public class SubMode extends Mode {
    public SubMode(String name) {
        super(name, null);
    }
}