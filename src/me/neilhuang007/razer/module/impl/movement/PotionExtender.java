package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.potion.PotionEffect;
import util.time.StopWatch;

import java.util.HashMap;

/**
 * @author Auth
 * @since 23/07/2022
 */
@Rise
@ModuleInfo(name = "module.movement.potionextender.name", description = "module.movement.potionextender.description", category = Category.MOVEMENT)
public class PotionExtender extends Module {

    private final NumberValue extendDuration = new NumberValue("Extend Duration", this, 10, 1, 60, 1);

    public final HashMap<PotionEffect, StopWatch> potions = new HashMap<>();

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        for (final PotionEffect potion : mc.thePlayer.getActivePotionEffects()) {
            if (potions.containsKey(potion)) {
                final StopWatch stopWatch = potions.get(potion);
                if (stopWatch.finished(this.extendDuration.getValue().longValue() * 1000)) {
                    mc.thePlayer.removePotionEffect(potion.getPotionID());
                    potions.remove(potion);
                }
            } else if (potion.duration == 1) {
                final StopWatch stopWatch = new StopWatch();
                stopWatch.reset();
                potions.put(potion, stopWatch);
            }
        }
    };
}