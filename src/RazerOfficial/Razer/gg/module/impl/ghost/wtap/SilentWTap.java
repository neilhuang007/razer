package RazerOfficial.Razer.gg.module.impl.ghost.wtap;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.player.AttackEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import RazerOfficial.Razer.gg.module.impl.ghost.WTap;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
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
