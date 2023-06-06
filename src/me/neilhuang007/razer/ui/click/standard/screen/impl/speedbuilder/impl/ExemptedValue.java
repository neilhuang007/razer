package me.neilhuang007.razer.ui.click.standard.screen.impl.speedbuilder.impl;

import me.neilhuang007.razer.ui.click.standard.screen.impl.speedbuilder.Modifier;
import me.neilhuang007.razer.ui.click.standard.screen.impl.speedbuilder.Tick;

/**
 * Author: Alan
 * Date: 28/03/2022
 */

public class ExemptedValue extends Modifier {

    public ExemptedValue(final Tick parent) {
        super(parent);
        this.name = "Exempted Value";
    }

    @Override
    public void move() {
        mc.thePlayer.motionY = 1;
    }
}
