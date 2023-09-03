package RazerOfficial.Razer.gg.ui.click.clover.setting.impl;

import RazerOfficial.Razer.gg.ui.click.clover.setting.api.SettingComp;
import RazerOfficial.Razer.gg.util.font.FontManager;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.value.Value;

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
