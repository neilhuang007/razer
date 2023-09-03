package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.phase.NormalPhase;
import RazerOfficial.Razer.gg.module.impl.movement.phase.WatchdogAutoPhase;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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