package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class NormalTower extends Mode<Scaffold> {

    public NormalTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.42F;
            }
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> packet = event.getPacket();

        if (mc.thePlayer.motionY > -0.0784000015258789 && packet instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

            if (wrapper.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4, mc.thePlayer.posZ))) {
                mc.thePlayer.motionY = -0.0784000015258789;
            }
        }
    };
}
