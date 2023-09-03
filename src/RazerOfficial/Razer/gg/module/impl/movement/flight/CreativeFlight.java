package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class CreativeFlight extends Mode<Flight> {

    public CreativeFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.capabilities.allowFlying = true;
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> p = event.getPacket();

        if (p instanceof S39PacketPlayerAbilities) {
            final S39PacketPlayerAbilities wrapper = (S39PacketPlayerAbilities) p;
            wrapper.setFlying(mc.thePlayer.capabilities.isFlying);
        }
    };

    @Override
    public void onEnable() {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        mc.thePlayer.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.allowFlying = mc.playerController.getCurrentGameType().isCreative();
        mc.thePlayer.capabilities.isFlying = false;
    }
}