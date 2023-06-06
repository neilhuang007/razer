package me.neilhuang007.razer.ui.click.dropdown;

import lombok.Getter;
import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.impl.render.ClickGUI;
import me.neilhuang007.razer.ui.click.dropdown.components.CategoryComponent;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DropdownClickGUI extends GuiScreen {

    private final List<CategoryComponent> categoryComponents = new ArrayList<>();

    @Getter
    private final Color mainColor = new Color(30, 30, 30);

    public DropdownClickGUI() {
        double posX = 20;

        for (final Category category : Category.values()) {
            switch (category) {
                case SEARCH:
                    continue;
            }

            this.categoryComponents.add(new CategoryComponent(category, posX));
            posX += 110;
        }
    }

    public void render() {
        for (final CategoryComponent category : categoryComponents) {
            category.render();
        }
    }

    public void bloom() {
        categoryComponents.forEach(CategoryComponent::bloom);
    }

    @Override
    public void onGuiClosed() {
        Client.INSTANCE.getModuleManager().get(ClickGUI.class).toggle();
    }
}
