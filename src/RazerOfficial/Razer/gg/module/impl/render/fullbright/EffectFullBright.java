package RazerOfficial.Razer.gg.module.impl.render.fullbright;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.impl.render.FullBright;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * @author Strikeless (mode), Patrick (implementation)
 * @since 04.11.2021
 */
public final class EffectFullBright extends Mode<FullBright> {

    public EffectFullBright(String name, FullBright parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 1));
    };

    @Override
    public void onDisable() {
        if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
            mc.thePlayer.removePotionEffect(Potion.nightVision.id);
        }
    }
}