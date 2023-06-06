package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.PingSpoofComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import me.neilhuang007.razer.util.math.MathUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;

/**
 * @author Alan
 * @since 23/10/2021
 */

@Rise
@ModuleInfo(name = "module.player.blink.name", description = "module.player.blink.description", category = Category.PLAYER)
public class Blink extends Module {

    public BooleanValue pulse = new BooleanValue("Pulse", this, false);
    public BooleanValue incoming = new BooleanValue("Incoming", this, false);
    public BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 2, 2, 2, 40, 1, () -> !pulse.getValue());
    public int next;
    private EntityOtherPlayerMP blinkEntity;

    @Override
    protected void onEnable() {
        getNext();
        spawnEntity();
    }

    @Override
    protected void onDisable() {
        deSpawnEntity();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        PingSpoofComponent.setSpoofing(999999999, incoming.getValue(), incoming.getValue(), incoming.getValue(), incoming.getValue(), incoming.getValue(), true);

        if (mc.thePlayer.ticksExisted > next && pulse.getValue()) {
            getNext();
            PingSpoofComponent.dispatch();

            deSpawnEntity();
            spawnEntity();
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        getNext();
    };

    public void getNext() {
        if (mc.thePlayer == null) return;
        next = mc.thePlayer.ticksExisted + (int) MathUtil.getRandom(delay.getValue().intValue(), delay.getSecondValue().intValue());
    }

    public void deSpawnEntity() {
        if (blinkEntity != null) {
            Client.INSTANCE.getBotManager().remove(blinkEntity);
            mc.theWorld.removeEntityFromWorld(blinkEntity.getEntityId());
            blinkEntity = null;
        }
    }

    public void spawnEntity() {
        if (blinkEntity == null) {
//            blinkEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
//            blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
//            blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
//            blinkEntity.setSprinting(mc.thePlayer.isSprinting());
//            blinkEntity.setInvisible(mc.thePlayer.isInvisible());
//            blinkEntity.setSneaking(mc.thePlayer.isSneaking());
//            blinkEntity.inventory = mc.thePlayer.inventory;
//            Client.INSTANCE.getBotManager().add(blinkEntity);
//
//            mc.theWorld.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        }
    }
}