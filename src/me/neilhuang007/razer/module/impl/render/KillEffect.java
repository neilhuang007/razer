package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.util.sound.SoundUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.EnumParticleTypes;

@ModuleInfo(name = "module.render.killeffect.name", description = "module.render.killeffect.description", category = Category.RENDER)
public final class KillEffect extends Module {

    private final BooleanValue lightning = new BooleanValue("Lightning", this, true);
    private final BooleanValue explosion = new BooleanValue("Explosion", this, true);

    private EntityLivingBase target;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (this.target != null && !mc.theWorld.loadedEntityList.contains(this.target)) {
            if (this.lightning.getValue()) {
                final EntityLightningBolt entityLightningBolt = new EntityLightningBolt(mc.theWorld, target.posX, target.posY, target.posZ);
                mc.theWorld.addEntityToWorld((int) (-Math.random() * 100000), entityLightningBolt);

                SoundUtil.playSound("ambient.weather.thunder");
            }

            if (this.explosion.getValue()) {
                for (int i = 0; i <= 8; i++) {
                    mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.FLAME);
                }

                SoundUtil.playSound("item.fireCharge.use");
            }

            this.target = null;
        }
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {

        final Entity entity = event.getTarget();

        if (entity instanceof EntityLivingBase) {
            target = (EntityLivingBase) entity;
        }
    };
}