package RazerOfficial.Razer.gg.module.impl.combat.antibot;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.AntiBot;
import RazerOfficial.Razer.gg.value.Mode;

public final class AdvancedAntiBot extends Mode<AntiBot> {

    public AdvancedAntiBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            if (mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 200) {
                Razer.INSTANCE.getBotManager().remove(player);
            }

            if (player.ticksExisted < 5 || player.isInvisible() || mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 100 * 100) {
                Razer.INSTANCE.getBotManager().add(player);
            }
        });
    };

}