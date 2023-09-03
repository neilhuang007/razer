package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.inventorymove.BufferAbuseInventoryMove;
import RazerOfficial.Razer.gg.module.impl.movement.inventorymove.CancelInventoryMove;
import RazerOfficial.Razer.gg.module.impl.movement.inventorymove.NormalInventoryMove;
import RazerOfficial.Razer.gg.module.impl.movement.inventorymove.WatchdogInventoryMove;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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
