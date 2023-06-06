package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.ghost.wtap.LegitWTap;
import me.neilhuang007.razer.module.impl.ghost.wtap.SilentWTap;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.NumberValue;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.wtap.name", description = "module.ghost.wtap.description", category = Category.GHOST)
public class WTap extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new LegitWTap("Legit", this))
            .add(new SilentWTap("Silent", this))
            .setDefault("Legit");

    public final NumberValue chance = new NumberValue("WTap Chance", this, 100, 0, 100, 1);
}