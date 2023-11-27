package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.HurtRenderEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ColorValue;

import java.awt.*;

/**
 * @author Alan
 * @since 28/05/2022
 */

@ModuleInfo(name = "Damage Tint", description = "Changes Damage Color", category = Category.RENDER)
public final class HurtColor extends Module {

    private final ColorValue color = new ColorValue("Hurt Color",this, Color.decode("#a08fffe2"));
    private final BooleanValue oldDamage = new BooleanValue("1.7 Damage Animation", this, true);


    @EventLink()
    public final Listener<HurtRenderEvent> onHurtRender = event -> {
        event.setOldDamage(oldDamage.getValue());
    };
}