package me.neilhuang007.razer.module.impl.ghost.wtap;

import me.neilhuang007.razer.module.impl.ghost.WTap;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public final class SilentWTap extends Mode<WTap> {

    private boolean sprinting, wTap;
    private int ticks;

    public SilentWTap(String name, WTap parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        ticks = 0;
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        ticks++;

        switch (ticks) {
            case 1:
                wTap = Math.random() * 100 < getParent().chance.getValue().doubleValue();
                if (sprinting) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                } else {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                break;

            case 2:
                if (!sprinting) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                break;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof C0BPacketEntityAction) {
            final C0BPacketEntityAction wrapper = (C0BPacketEntityAction) packet;

            switch (wrapper.getAction()) {
                case START_SPRINTING:
                    sprinting = true;
                    break;

                case STOP_SPRINTING:
                    sprinting = false;
                    break;
            }
        }
    };
}
