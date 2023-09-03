package RazerOfficial.Razer.gg.module.impl.render.fullbright;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.impl.render.FullBright;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Strikeless
 * @since 04.11.2021
 */
public final class GammaFullBright extends Mode<FullBright> {

    private float oldGamma;

    public GammaFullBright(String name, FullBright parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        mc.gameSettings.gammaSetting = 100.0F;
    };

    @Override
    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = oldGamma;
    }
}