package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;


/**
 * @author Alan
 * @since 28/05/2022
 */

@ModuleInfo(name = "module.render.particles.name", description = "module.render.particles.description", category = Category.RENDER)
public final class Particles extends Module {

    private final NumberValue multiplier = new NumberValue("Multiplier", this, 1, 1, 10, 1);
    private final BooleanValue alwaysCrit = new BooleanValue("Always Crit", this, true);

    private final BooleanValue alwaysSharpness = new BooleanValue("Always Sharpness", this, true);

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        Entity target = event.getTarget();

        if (mc.thePlayer.fallDistance > 0 || alwaysCrit.getValue() || alwaysSharpness.getValue()) {
            for (int i = 0; i <= multiplier.getValue().intValue(); i++) {
                if (this.alwaysCrit.getValue()) {
                    mc.thePlayer.onCriticalHit(target);
                }

                if (this.alwaysSharpness.getValue()) {
                    this.mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
                }
            }
        }
    };
}