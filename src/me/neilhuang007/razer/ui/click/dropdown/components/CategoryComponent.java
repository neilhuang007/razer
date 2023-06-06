package me.neilhuang007.razer.ui.click.dropdown.components;

import lombok.Getter;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.ui.click.dropdown.DropdownClickGUI;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.render.RenderUtil;

@Getter
public class CategoryComponent implements InstanceAccess {

    private final Category category;
    private final double posX;

    private final boolean expanded = true;
    private boolean selected;

    public CategoryComponent(final Category category, final double posX) {
        this.category = category;
        this.posX = posX;
    }

    public void render() {
        final DropdownClickGUI dropdownClickGUI = this.getDropdownClickGUI();

        RenderUtil.roundedRectangle(posX, 20, 90, 20, 5, dropdownClickGUI.getMainColor());
        RenderUtil.rectangle(posX, 25, 90, 15, dropdownClickGUI.getMainColor());
        this.nunitoNormal.drawString(category.getName(), (float) (posX + 4), 26.5F, -1);
    }

    public void bloom() {

    }
}
