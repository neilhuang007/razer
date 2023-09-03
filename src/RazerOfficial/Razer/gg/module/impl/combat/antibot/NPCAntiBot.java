package RazerOfficial.Razer.gg.module.impl.combat.antibot;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.AntiBot;
import RazerOfficial.Razer.gg.value.Mode;

public final class NPCAntiBot extends Mode<AntiBot> {

    public NPCAntiBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            if (!player.moved) {
                Razer.INSTANCE.getBotManager().add(player);
            }
        });
    };
}