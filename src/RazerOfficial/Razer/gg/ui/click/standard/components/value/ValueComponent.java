package RazerOfficial.Razer.gg.ui.click.standard.components.value;

import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import RazerOfficial.Razer.gg.value.Value;
import lombok.Getter;

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
