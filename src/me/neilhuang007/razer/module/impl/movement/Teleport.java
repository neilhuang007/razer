package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.teleport.WatchdogTeleport;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Alan
 * @since 18/11/2022
 */

@Rise
@ModuleInfo(name = "module.movement.teleport.name", description = "module.movement.teleport.description", category = Category.MOVEMENT)
public class Teleport extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new WatchdogTeleport("Watchdog", this))
            .setDefault("Vanilla");

}