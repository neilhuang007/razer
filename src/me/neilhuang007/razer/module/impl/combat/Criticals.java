package me.neilhuang007.razer.module.impl.combat;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.combat.criticals.EditCriticals;
import me.neilhuang007.razer.module.impl.combat.criticals.NoGroundCriticals;
import me.neilhuang007.razer.module.impl.combat.criticals.PacketCriticals;
import me.neilhuang007.razer.module.impl.combat.criticals.WatchdogCriticals;
import me.neilhuang007.razer.value.impl.ModeValue;

@Rise
@ModuleInfo(name = "module.combat.criticals.name", description = "module.combat.criticals.description", category = Category.COMBAT)
public final class Criticals extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketCriticals("Packet", this))
            .add(new EditCriticals("Edit", this))
            .add(new NoGroundCriticals("No Ground", this))
            .add(new WatchdogCriticals("Watchdog", this))
            .setDefault("Packet");
}
