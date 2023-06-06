package me.neilhuang007.razer.module.impl.combat.antibot;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.module.impl.combat.AntiBot;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;

public final class NPCAntiBot extends Mode<AntiBot> {

    public NPCAntiBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            if (!player.moved) {
                Client.INSTANCE.getBotManager().add(player);
            }
        });
    };
}