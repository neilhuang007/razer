package me.neilhuang007.razer.ui.click.standard.components.value;

import lombok.Getter;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.vector.Vector2d;
import me.neilhuang007.razer.value.Value;

@Getter
public abstract class ValueComponent implements InstanceAccess {

    public double height = 14;
    public Vector2d position;
    public Value<?> value;

    public ValueComponent(final Value<?> value) {
        this.value = value;
    }

    public abstract void draw(Vector2d position, int mouseX, int mouseY, float partialTicks);

    public abstract void click(int mouseX, int mouseY, int mouseButton);

    public abstract void released();

    public abstract void bloom();

    public abstract void key(final char typedChar, final int keyCode);
}
