package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.sneak.HoldSneak;
import me.neilhuang007.razer.module.impl.movement.sneak.NCPSneak;
import me.neilhuang007.razer.module.impl.movement.sneak.StandardSneak;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Auth
 * @since 25/06/2022
 */
@Rise
@ModuleInfo(name = "module.movement.sneak.name", description = "module.movement.sneak.description", category = Category.MOVEMENT)
public class Sneak extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardSneak("Standard", this))
            .add(new HoldSneak("Hold", this))
            .add(new NCPSneak("NCP", this))
            .setDefault("Standard");
}