package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class MMCTower extends Mode<Scaffold> {

    public MMCTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (mc.gameSettings.keyBindJump.isKeyDown() && packet instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = ((C08PacketPlayerBlockPlacement) packet);

            if (c08PacketPlayerBlockPlacement.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4, mc.thePlayer.posZ))) {
                mc.gameSettings.keyBindSprint.setPressed(false);
                mc.thePlayer.setSprinting(false);
                mc.thePlayer.motionY = 0.42F;
            }
        }
    };
}
