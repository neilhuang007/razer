package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.sneak.HoldSneak;
import RazerOfficial.Razer.gg.module.impl.movement.sneak.NCPSneak;
import RazerOfficial.Razer.gg.module.impl.movement.sneak.StandardSneak;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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