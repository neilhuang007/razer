package me.neilhuang007.razer.module.impl.combat.velocity;

import me.neilhuang007.razer.module.impl.combat.Velocity;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class MMCVelocity extends Mode<Velocity> {

    private int velocity;

    public MMCVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.velocity++;
    };


    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        final Packet<?> packet = event.getPacket();

        if (this.velocity > 20) {
            if (packet instanceof S12PacketEntityVelocity) {
                final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

                if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                    event.setCancelled(true);
                    this.velocity = 0;
                }
            } else if (packet instanceof S27PacketExplosion) {
                event.setCancelled(true);
                this.velocity = 0;
            }
        }
    };
}