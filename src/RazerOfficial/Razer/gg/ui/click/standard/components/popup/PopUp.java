package RazerOfficial.Razer.gg.ui.click.standard.components.popup;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.ui.click.standard.RiseClickGUI;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;

import java.awt.*;

/**
 * Author: Alan
 * Date: 28/03/2022
 */

public class PopUp implements InstanceAccess {
    public Vector2f scale;

    public void draw() {
        final RiseClickGUI clickGUI = Razer.INSTANCE.getStandardClickGUI();

        final double x = clickGUI.position.x;
        final double y = clickGUI.position.y;
        final double width = clickGUI.scale.x;
        final double height = clickGUI.scale.y;

//        RenderUtil.rectangle(x, y, width, height, new Color(0, 0, 0, 100));

        if (scale == null) return;

        RenderUtil.dropShadow(60, (float) (x + width / 2 - scale.x / 2), (float) (y + height / 2 - scale.y / 2),
                scale.x, scale.y, 50, 34);

        RenderUtil.roundedRectangle(x + width / 2 - scale.x / 2, y + height / 2 - scale.y / 2, scale.x, scale.y,
                9, new Color(0, 0, 0, 230));


    }
}
