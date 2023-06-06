package me.neilhuang007.razer.module.impl.combat;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.combat.regen.VanillaRegen;
import me.neilhuang007.razer.module.impl.combat.regen.WorldGuardRegen;
import me.neilhuang007.razer.value.impl.ModeValue;

@Rise
@ModuleInfo(name = "module.combat.regen.name", description = "module.combat.regen.description", category = Category.COMBAT)
public final class Regen extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaRegen("Vanilla", this))
            .add(new WorldGuardRegen("World Guard", this))
            .setDefault("Vanilla");
}
