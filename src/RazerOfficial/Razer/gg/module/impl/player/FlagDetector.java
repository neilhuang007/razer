package RazerOfficial.Razer.gg.module.impl.player;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.player.flagdetector.Flight;
import RazerOfficial.Razer.gg.module.impl.player.flagdetector.Friction;
import RazerOfficial.Razer.gg.module.impl.player.flagdetector.MathGround;
import RazerOfficial.Razer.gg.module.impl.player.flagdetector.Strafe;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;

@Rise
@ModuleInfo(name = "module.player.flagdetector.name", description = "module.player.flagdetector.description", category = Category.PLAYER)
public class FlagDetector extends Module {
    public BooleanValue strafe = new BooleanValue("Strafe (Watchdog)", this, false, new Strafe("", this));
    public BooleanValue friction = new BooleanValue("Friction", this, false, new Friction("", this));
    public BooleanValue flight = new BooleanValue("Flight", this, false, new Flight("", this));
    public BooleanValue mathGround = new BooleanValue("Math Ground", this, false, new MathGround("", this));

    @Override
    protected void onEnable() {
        if (!Razer.DEVELOPMENT_SWITCH) {
            ChatUtil.display("This module is only enabled for developers or config makersconfig");

            toggle();
        }
    }

    public void fail(String check) {
        ChatUtil.display("§7failed " + Razer.INSTANCE.getThemeManager().getTheme().getChatAccentColor() + check);
    }
}
