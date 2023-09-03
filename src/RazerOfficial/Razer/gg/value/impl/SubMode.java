package RazerOfficial.Razer.gg.value.impl;

import RazerOfficial.Razer.gg.value.Mode;

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