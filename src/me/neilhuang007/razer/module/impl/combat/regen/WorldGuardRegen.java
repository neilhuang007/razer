package me.neilhuang007.razer.module.impl.combat.regen;

import me.neilhuang007.razer.module.impl.combat.Regen;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public final class WorldGuardRegen extends Mode<Regen> {

    private final NumberValue health = new NumberValue("Minimum Health", this, 15, 1, 20, 1);

    private int ticks;

    private float yaw;
    private float pitch;

    public WorldGuardRegen(String name, Regen parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue()) {
            if (ticks <= 1) {
                event.setPosY(event.getPosY() - 0.05);
                event.setYaw(yaw);
                event.setPitch(pitch);
                ticks++;
            }
        } else {
            yaw = event.getYaw();
            pitch = event.getPitch();
            ticks = 0;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> p = event.getPacket();

        if (p instanceof C02PacketUseEntity && mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            final C02PacketUseEntity wrapper = (C02PacketUseEntity) p;

            if (wrapper.getAction().equals(C02PacketUseEntity.Action.ATTACK)) {
                event.setCancelled(true);

                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                PacketUtil.sendNoEvent(wrapper);
                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, false));
            }
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        if (event.getPacket() instanceof S2DPacketOpenWindow && ticks <= 1) {
            event.setCancelled(true);
        }
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            event.setSpeed(0);
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < this.health.getValue().floatValue() && ticks <= 1) {
            event.setJump(false);
        }
    };
}