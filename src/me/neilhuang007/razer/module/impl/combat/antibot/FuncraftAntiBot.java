package me.neilhuang007.razer.module.impl.combat.antibot;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.module.impl.combat.AntiBot;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.value.Mode;

/**
 * @author Wykt
 * @since 2/04/2023
 */

public final class FuncraftAntiBot extends Mode<AntiBot> {
    public FuncraftAntiBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink
    private final Listener<PreUpdateEvent> preUpdateEventListener = event -> {
        mc.theWorld.playerEntities.forEach(player -> {
            if(player.getDisplayName().getUnformattedText().contains("§")) {
                Client.INSTANCE.getBotManager().remove(player);
                return;
            }

            Client.INSTANCE.getBotManager().add(player);
        });
    };
}