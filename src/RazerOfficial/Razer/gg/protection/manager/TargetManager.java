package RazerOfficial.Razer.gg.protection.manager;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.impl.combat.KillAura;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author Alan
 * @since 3/03/2022
 */
public class TargetManager extends ConcurrentLinkedQueue<EntityLivingBase> implements InstanceAccess {

    boolean players;
    boolean invisibles;
    boolean animals;
    boolean mobs;
    boolean teams;

    private int loadedEntitySize;

    public void init() {
        Razer.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if (mc.thePlayer.ticksExisted % 150 == 0 || loadedEntitySize != mc.theWorld.loadedEntityList.size()) {
            this.updateTargets();
            loadedEntitySize = mc.theWorld.loadedEntityList.size();
        }
    };

    public void updateTargets() {
        try {
            KillAura killAura = getModule(KillAura.class);
            players = killAura.player.getValue();
            invisibles = killAura.invisibles.getValue();
            animals = killAura.animals.getValue();
            mobs = killAura.mobs.getValue();
            teams = killAura.teams.getValue();

        } catch (Exception ignored) {
            // Don't give crackers clues...
            if (Razer.DEVELOPMENT_SWITCH) ignored.printStackTrace();
        }
    }

    public List<Entity> getTargets(final double range) {
        return mc.theWorld.loadedEntityList.stream()
                // must be a player, not a sheep or somethin
                .filter(entity -> entity instanceof EntityPlayer)
                // not ourselfs
                .filter(entity -> entity != mc.thePlayer)
                // no blink entity
                .filter(entity -> !Razer.INSTANCE.getBotManager().contains(entity))
                // no dead entities
                .filter(entity -> entity.isEntityAlive())
                // must be in distance
                .filter(entity -> mc.thePlayer.getDistanceSqToEntity(entity) <= range)
                // sort usin distance
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceSqToEntity(entity)))
                // return a list
                .collect(Collectors.toList());
    }

    public List<Entity> getTarget(final double range) {
        return mc.theWorld.loadedEntityList.stream()
                // must be a player, not a sheep or somethin
                .filter(entity -> entity instanceof EntityPlayer)
                // not ourselfs
                .filter(entity -> entity != mc.thePlayer)
                // no blink entity
                .filter(entity -> !Razer.INSTANCE.getBotManager().contains(entity))
                // no dead entities
                .filter(entity -> entity.isEntityAlive())
                // must be in distance
                .filter(entity -> mc.thePlayer.getDistanceSqToEntity(entity) <= range)
                // sort usin distance
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceSqToEntity(entity)))
                // return a list
                .collect(Collectors.toList());
    }
}