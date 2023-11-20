package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.combat.criticals.*;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

@Razer
@ModuleInfo(name = "module.combat.criticals.name", description = "module.combat.criticals.description", category = Category.COMBAT)
public final class Criticals extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketCriticals("Packet", this))
            .add(new EditCriticals("Edit", this))
            .add(new NoGroundCriticals("No Ground", this))
            .add(new WatchdogCriticals("Watchdog", this))
            .add(new DCJCriticals("DCJ", this))
            .setDefault("Packet");
}
