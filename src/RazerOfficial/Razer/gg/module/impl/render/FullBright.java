package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.render.fullbright.EffectFullBright;
import RazerOfficial.Razer.gg.module.impl.render.fullbright.GammaFullBright;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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