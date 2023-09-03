package RazerOfficial.Razer.gg.ui.click.standard.components.value.impl;

import RazerOfficial.Razer.gg.ui.click.standard.components.value.ValueComponent;
import RazerOfficial.Razer.gg.util.gui.textbox.TextAlign;
import RazerOfficial.Razer.gg.util.gui.textbox.TextBox;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import RazerOfficial.Razer.gg.value.Value;
import RazerOfficial.Razer.gg.value.impl.StringValue;

import java.awt.*;

public class StringValueComponent extends ValueComponent {

    public final TextBox textBox = new TextBox(new Vector2d(200, 200), nunitoSmall, Color.WHITE, TextAlign.LEFT, "", 20);

    public StringValueComponent(final Value<?> value) {
        super(value);

        final StringValue stringValue = (StringValue) value;
        textBox.setText(stringValue.getValue());
        textBox.setCursor(stringValue.getValue().length());
    }

    @Override
    public void draw(Vector2d position, int mouseX, int mouseY, float partialTicks) {
        this.position = position;
        final StringValue stringValue = (StringValue) this.value;

        this.height = 28;

        // Draws name
        this.nunitoSmall.drawString(this.value.getName(), this.position.x, this.position.y, this.getStandardClickGUI().fontDarkColor.hashCode());

        // Draws value
        this.position = new Vector2d(this.position.x, this.position.y + 14);
        this.textBox.setPosition(this.position);
        this.textBox.setWidth(242.5f - 12);
        this.textBox.draw();
        stringValue.setValue(textBox.getText());
    }

    @Override
    public void click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return;
        }

        textBox.click(mouseX, mouseY, mouseButton);
    }

    @Override
    public void released() {
    }

    @Override
    public void bloom() {
    }

    @Override
    public void key(final char typedChar, final int keyCode) {
        if (this.position == null) {
            return;
        }

        textBox.key(typedChar, keyCode);
    }
}
