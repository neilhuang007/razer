package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.combat.criticals.EditCriticals;
import RazerOfficial.Razer.gg.module.impl.combat.criticals.NoGroundCriticals;
import RazerOfficial.Razer.gg.module.impl.combat.criticals.PacketCriticals;
import RazerOfficial.Razer.gg.module.impl.combat.criticals.WatchdogCriticals;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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