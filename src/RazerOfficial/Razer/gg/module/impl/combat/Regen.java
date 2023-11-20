package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.combat.regen.VanillaRegen;
import RazerOfficial.Razer.gg.module.impl.combat.regen.WorldGuardRegen;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

@Razer
@ModuleInfo(name = "module.combat.regen.name", description = "module.combat.regen.description", category = Category.COMBAT)
public final class Regen extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaRegen("Vanilla", this))
            .add(new WorldGuardRegen("World Guard", this))
            .setDefault("Vanilla");
}
