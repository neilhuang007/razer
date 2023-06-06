package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.phase.NormalPhase;
import me.neilhuang007.razer.module.impl.movement.phase.WatchdogAutoPhase;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */

@Rise
@ModuleInfo(name = "module.movement.phase.name", description = "module.movement.phase.description", category = Category.MOVEMENT)
public class Phase extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new NormalPhase("Normal", this))
            .add(new WatchdogAutoPhase("Watchdog Auto Phase", this))
            .setDefault("Normal");

}