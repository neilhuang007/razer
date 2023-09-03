package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;
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

        if (InstanceAccess.mc.gameSettings.keyBindJump.isKeyDown() && packet instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement = ((C08PacketPlayerBlockPlacement) packet);

            if (c08PacketPlayerBlockPlacement.getPosition().equals(new BlockPos(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY - 1.4, InstanceAccess.mc.thePlayer.posZ))) {
                InstanceAccess.mc.gameSettings.keyBindSprint.setPressed(false);
                InstanceAccess.mc.thePlayer.setSprinting(false);
                InstanceAccess.mc.thePlayer.motionY = 0.42F;
            }
        }
    };
}
