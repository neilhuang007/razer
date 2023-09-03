package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.wallclimb.*;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */
@Rise
@ModuleInfo(name = "module.movement.wallclimb.name", description = "module.movement.wallclimb.description", category = Category.MOVEMENT)
public class WallClimb extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VulcanWallClimb("Vulcan", this))
            .add(new VerusWallClimb("Verus", this))
            .add(new KauriWallClimb("Kauri", this))
            .add(new WatchdogWallClimb("Watchdog", this))
            .add(new MineMenClubWallClimb("MineMenClub", this))
            .setDefault("Vulcan");
}