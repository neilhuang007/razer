package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.noslow.*;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */
@Rise
@ModuleInfo(name = "module.movement.noslow.name", description = "module.movement.noslow.description", category = Category.MOVEMENT)
public class NoSlow extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaNoSlow("Vanilla", this))
            .add(new NCPNoSlow("NCP", this))
            .add(new NewNCPNoSlow("New NCP", this))
            .add(new IntaveNoSlow("Intave", this))
            .add(new OldIntaveNoSlow("Old Intave", this))
            .add(new VariableNoSlow("Variable", this))
            .add(new PredictionNoSlow("Prediction", this))
            .setDefault("Vanilla");
}