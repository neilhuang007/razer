package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.render.fullbright.EffectFullBright;
import me.neilhuang007.razer.module.impl.render.fullbright.GammaFullBright;
import me.neilhuang007.razer.value.impl.ModeValue;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Rise
@ModuleInfo(name = "module.render.fullbright.name", description = "module.render.fullbright.description", category = Category.RENDER)
public final class FullBright extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new GammaFullBright("Gamma", this))
            .add(new EffectFullBright("Effect", this))
            .setDefault("Gamma");
}