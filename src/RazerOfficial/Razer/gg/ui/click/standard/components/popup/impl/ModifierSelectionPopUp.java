package RazerOfficial.Razer.gg.ui.click.standard.components.popup.impl;

import RazerOfficial.Razer.gg.ui.click.standard.components.popup.PopUp;
import RazerOfficial.Razer.gg.util.vector.Vector2f;

/**
 * Author: Alan
 * Date: 29/03/2022
 */

public class ModifierSelectionPopUp extends PopUp {
    @Override
    public void draw() {
        this.scale = new Vector2f(200, 120);
        super.draw();
    }
}
