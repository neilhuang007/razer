package RazerOfficial.Razer.gg.ui.click.dropdown;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.impl.render.ClickGUI;
import RazerOfficial.Razer.gg.ui.click.dropdown.components.CategoryComponent;
import lombok.Getter;
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
        Razer.INSTANCE.getModuleManager().get(ClickGUI.class).toggle();
    }
}
