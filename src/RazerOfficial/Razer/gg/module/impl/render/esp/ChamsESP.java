package RazerOfficial.Razer.gg.module.impl.render.esp;

import RazerOfficial.Razer.gg.component.impl.render.ESPComponent;
import RazerOfficial.Razer.gg.component.impl.render.espcomponent.api.ESPColor;
import RazerOfficial.Razer.gg.component.impl.render.espcomponent.impl.PlayerChams;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.module.impl.render.ESP;
import RazerOfficial.Razer.gg.value.Mode;

import java.awt.*;

public final class ChamsESP extends Mode<ESP> {

    public ChamsESP(String name, ESP parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Color color = getTheme().getFirstColor();
        ESPComponent.add(new PlayerChams(new ESPColor(color, color, color)));
    };
}