package me.neilhuang007.razer.module.impl.movement.longjump;

import me.neilhuang007.razer.component.impl.player.ItemDamageComponent;
import me.neilhuang007.razer.component.impl.player.PingSpoofComponent;
import me.neilhuang007.razer.module.impl.movement.LongJump;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class WatchdogLongJump extends Mode<LongJump> {
    private boolean aBoolean;
    private double aDouble;

    public WatchdogLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PacketReceiveEvent> receive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                aDouble = wrapper.motionY / 8000.0D;
                if (aDouble > 0.1 - Math.random() / 10000f) {
                    aBoolean = true;
                    event.setCancelled(true);
                }
            }
        }
    };

    @EventLink
    public final Listener<PreUpdateEvent> preUpdate = event -> {
        PingSpoofComponent.setSpoofing(2000, true, false, false, false, false);

        if (!aBoolean) return;

        if (mc.thePlayer.ticksSinceVelocity == 1) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.75 : 0.7) - Math.random() / 10000f);
            mc.thePlayer.jump();
        }

        if (mc.thePlayer.ticksSinceVelocity == 9) {
            MoveUtil.strafe((mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.8 : 0.7) - Math.random() / 10000f);
            mc.thePlayer.motionY = aDouble;
        }

        if (mc.thePlayer.ticksSinceVelocity <= 50 && mc.thePlayer.ticksSinceVelocity > 9) {
            mc.thePlayer.motionY += 0.028;

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                mc.thePlayer.motionX *= 1.038;
                mc.thePlayer.motionZ *= 1.038;
            } else {
                if (mc.thePlayer.ticksSinceVelocity == 12 || mc.thePlayer.ticksSinceVelocity == 13) {
                    mc.thePlayer.motionX *= 1.1;
                    mc.thePlayer.motionZ *= 1.1;
                }

                mc.thePlayer.motionX *= 1.019;
                mc.thePlayer.motionZ *= 1.019;
            }
        }

    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!aBoolean) {
            event.setCancelled(true);
            MoveUtil.stop();
        } else if (mc.thePlayer.ticksSinceVelocity <= 19) {
            MoveUtil.strafe();
        }
    };

    @Override
    public void onEnable() {
        ItemDamageComponent.damage(false);
        aBoolean = false;
    }
}