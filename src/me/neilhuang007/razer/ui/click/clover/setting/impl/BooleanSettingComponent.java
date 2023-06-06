package me.neilhuang007.razer.ui.click.clover.setting.impl;

import me.neilhuang007.razer.ui.click.clover.setting.api.SettingComp;
import me.neilhuang007.razer.util.font.FontManager;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.render.ColorUtil;
import me.neilhuang007.razer.util.render.RenderUtil;
import me.neilhuang007.razer.value.Value;

import java.awt.*;

public class BooleanSettingComponent extends SettingComp implements InstanceAccess {
    public BooleanSettingComponent(Value value) {
        super(value);
    }

    @Override
    public void render() {
        FontManager.getNunito(20).drawString(value.getName(), position.x, position.y, Color.WHITE.hashCode());

        RenderUtil.roundedRectangle(position.x, position.y, 20, 8, 4, ColorUtil.withAlpha(Color.WHITE, 100));
    }
}
