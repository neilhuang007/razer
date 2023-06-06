package me.neilhuang007.razer.module.impl.combat;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.combat.antibot.*;
import me.neilhuang007.razer.value.impl.BooleanValue;

@Rise
@ModuleInfo(name = "module.combat.antibot.name", description = "module.combat.antibot.description", category = Category.COMBAT)
public final class AntiBot extends Module {

    private final BooleanValue advancedAntiBot = new BooleanValue("Always Nearby Check", this, false,
            new AdvancedAntiBot("", this));

    private final BooleanValue watchdogAntiBot = new BooleanValue("Watchdog Check", this, false,
            new WatchdogAntiBot("", this));

    private final BooleanValue funcraftAntiBot = new BooleanValue("Funcraft Check", this, false,
            new FuncraftAntiBot("", this));

    private final BooleanValue ncps = new BooleanValue("NPC Detection Check", this, false,
            new NPCAntiBot("", this));

    private final BooleanValue middleClick = new BooleanValue("Middle Click Bot", this, false,
            new MiddleClickBot("", this));

    @Override
    protected void onDisable() {
        Client.INSTANCE.getBotManager().clear();
    }
}
