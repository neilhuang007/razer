package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.render.esp.ChamsESP;
import RazerOfficial.Razer.gg.module.impl.render.esp.GlowESP;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;

@Rise
@ModuleInfo(name = "module.render.esp.name", description = "module.render.esp.description", category = Category.RENDER)
public final class ESP extends Module {

    private BooleanValue glowESP = new BooleanValue("Glow", this, false, new GlowESP("", this));
    private BooleanValue chamsESP = new BooleanValue("Chams", this, false, new ChamsESP("", this));

}
