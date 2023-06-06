package me.neilhuang007.razer.module.impl.render.esp;

import me.neilhuang007.razer.component.impl.render.ESPComponent;
import me.neilhuang007.razer.component.impl.render.espcomponent.api.ESPColor;
import me.neilhuang007.razer.component.impl.render.espcomponent.impl.PlayerChams;
import me.neilhuang007.razer.module.impl.render.ESP;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.value.Mode;

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