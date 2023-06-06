package me.neilhuang007.razer.ui.menu.component;

import lombok.Getter;
import me.neilhuang007.razer.ui.menu.MenuColors;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

@Getter
public class MenuComponent implements InstanceAccess, MenuColors {

    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public MenuComponent(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
