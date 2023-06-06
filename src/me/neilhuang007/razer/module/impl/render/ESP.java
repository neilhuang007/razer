package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.render.esp.ChamsESP;
import me.neilhuang007.razer.module.impl.render.esp.GlowESP;
import me.neilhuang007.razer.value.impl.BooleanValue;

@Rise
@ModuleInfo(name = "module.render.esp.name", description = "module.render.esp.description", category = Category.RENDER)
public final class ESP extends Module {

    private BooleanValue glowESP = new BooleanValue("Glow", this, false, new GlowESP("", this));
    private BooleanValue chamsESP = new BooleanValue("Chams", this, false, new ChamsESP("", this));

}
