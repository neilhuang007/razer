package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.inventorymove.BufferAbuseInventoryMove;
import me.neilhuang007.razer.module.impl.movement.inventorymove.CancelInventoryMove;
import me.neilhuang007.razer.module.impl.movement.inventorymove.NormalInventoryMove;
import me.neilhuang007.razer.module.impl.movement.inventorymove.WatchdogInventoryMove;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */

@Rise
@ModuleInfo(name = "module.movement.inventorymove.name", description = "module.movement.inventorymove.description", category = Category.MOVEMENT)
public class InventoryMove extends Module {

    private final ModeValue bypassMode = new ModeValue("Bypass Mode", this)
            .add(new NormalInventoryMove("Normal", this))
            .add(new BufferAbuseInventoryMove("Buffer Abuse", this))
            .add(new CancelInventoryMove("Cancel", this))
            .add(new WatchdogInventoryMove("Watchdog", this))
            .setDefault("Normal");
}
