package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.player.flagdetector.Flight;
import me.neilhuang007.razer.module.impl.player.flagdetector.Friction;
import me.neilhuang007.razer.module.impl.player.flagdetector.MathGround;
import me.neilhuang007.razer.module.impl.player.flagdetector.Strafe;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;

@Rise
@ModuleInfo(name = "module.player.flagdetector.name", description = "module.player.flagdetector.description", category = Category.PLAYER)
public class FlagDetector extends Module {
    public BooleanValue strafe = new BooleanValue("Strafe (Watchdog)", this, false, new Strafe("", this));
    public BooleanValue friction = new BooleanValue("Friction", this, false, new Friction("", this));
    public BooleanValue flight = new BooleanValue("Flight", this, false, new Flight("", this));
    public BooleanValue mathGround = new BooleanValue("Math Ground", this, false, new MathGround("", this));

    @Override
    protected void onEnable() {
        if (!Client.DEVELOPMENT_SWITCH) {
            ChatUtil.display("This module is only enabled for developers or config makersconfig");

            toggle();
        }
    }

    public void fail(String check) {
        ChatUtil.display("§7failed " + Client.INSTANCE.getThemeManager().getTheme().getChatAccentColor() + check);
    }
}
