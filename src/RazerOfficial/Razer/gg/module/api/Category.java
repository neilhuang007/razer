package RazerOfficial.Razer.gg.module.api;

import RazerOfficial.Razer.gg.Type;
import RazerOfficial.Razer.gg.ui.click.standard.screen.Screen;
import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.CategoryScreen;
import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.LanguageScreen;
import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.SearchScreen;
import RazerOfficial.Razer.gg.ui.click.standard.screen.impl.ThemeScreen;
import RazerOfficial.Razer.gg.util.font.Font;
import RazerOfficial.Razer.gg.util.font.FontManager;
import lombok.Getter;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
public enum Category {
    SEARCH("category.search", FontManager.getIconsThree(17), "U", 0x1, new SearchScreen(), Type.BOTH),
    COMBAT("category.combat", FontManager.getIconsOne(17), "a", 0x2, new CategoryScreen(), Type.RISE),
    MOVEMENT("category.movement", FontManager.getIconsOne(17), "b", 0x3, new CategoryScreen(), Type.RISE),
    PLAYER("category.player", FontManager.getIconsOne(17), "c", 0x4, new CategoryScreen(), Type.RISE),
    RENDER("category.render", FontManager.getIconsOne(17), "g", 0x5, new CategoryScreen(), Type.RISE),
    EXPLOIT("category.exploit", FontManager.getIconsOne(17), "a", 0x6, new CategoryScreen(), Type.RISE),
    GHOST("category.ghost", FontManager.getIconsOne(17), "f", 0x7, new CategoryScreen(), Type.RISE),
    OTHER("category.other", FontManager.getIconsOne(17), "e", 0x8, new CategoryScreen(), Type.RISE),
    SCRIPT("category.script", FontManager.getIconsThree(17), "m", 0x7, new CategoryScreen(), Type.RISE),

    THEME("category.themes", FontManager.getIconsThree(17), "U", 0xA, new ThemeScreen(), Type.BOTH),

    LANGUAGE("category.language",FontManager.getIconsThree(17), "U",0xA,new LanguageScreen(), Type.BOTH);

    // name of category (in case we don't use enum names)
    private final String name;

    // icon character
    private final String icon;

    // optional color for every specific category (module list or click gui)
    private final int color;

    private final Font fontRenderer;

    public final Screen clickGUIScreen;
    public final Type clientType;

    Category(final String name, final Font fontRenderer, final String icon, final int color, final Screen clickGUIScreen, Type clientType) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.clickGUIScreen = clickGUIScreen;
        this.fontRenderer = fontRenderer;
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}