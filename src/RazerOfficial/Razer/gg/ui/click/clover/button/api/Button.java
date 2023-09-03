package RazerOfficial.Razer.gg.ui.click.clover.button.api;

import RazerOfficial.Razer.gg.util.dragging.Mouse;
import RazerOfficial.Razer.gg.util.gui.GUIUtil;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import util.type.EvictingList;

import java.awt.*;

public class Button {
    public Vector2d position, scale;
    public Runnable click;
    public EvictingList<ButtonEffect> buttonEffects = new EvictingList<>(3);

    public boolean down;

    public Button(Vector2d position, Vector2d scale, Runnable click) {
        this.position = position;
        this.scale = scale;
        this.click = click;
    }

    public void render() {
        buttonEffects.forEach(ButtonEffect::render);
    }

    public void click(final int mouseButton) {
        Vector2d mouse = Mouse.getMouse();

        if (GUIUtil.mouseOver(position, scale, mouse.x, mouse.y)) {
            buttonEffects.add(new ButtonEffect(this, ColorUtil.withAlpha(Color.BLACK, 100)));
            down = true;
            click.run();
        }
    }

    public void release() {
        down = false;
    }
}
