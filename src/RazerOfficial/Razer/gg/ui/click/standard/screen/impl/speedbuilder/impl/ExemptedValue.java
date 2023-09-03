package RazerOfficial.Razer.gg.ui.click.standard.screen.impl.speedbuilder.impl;

import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.speedbuilder.Modifier;
import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.speedbuilder.Tick;

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
