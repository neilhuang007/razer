package RazerOfficial.Razer.gg.module.impl.combat.antibot;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.AntiBot;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class WatchdogAntiBot extends Mode<AntiBot> {

    public WatchdogAntiBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());

            if (info == null) {
                Razer.INSTANCE.getBotManager().add(player);
            } else {
                Razer.INSTANCE.getBotManager().remove(player);
            }
        });
    };
}