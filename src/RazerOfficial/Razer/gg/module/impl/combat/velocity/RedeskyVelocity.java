package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class RedeskyVelocity extends Mode<Velocity> {

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    public RedeskyVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        final Packet<?> p = event.getPacket();

        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                if (horizontal == 0 && vertical == 0) {
                    event.setCancelled(true);
                    return;
                }

                wrapper.motionX *= horizontal / 100;
                wrapper.motionY *= vertical / 100;
                wrapper.motionZ *= horizontal / 100;

                event.setPacket(wrapper);

                PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
        }

        if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (horizontal == 0 && vertical == 0) {
                event.setCancelled(true);
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
        }
    };
}