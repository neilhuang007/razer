package RazerOfficial.Razer.gg.module.impl.movement.speed;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Speed;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

/**
 * @author Auth
 * @since 4/07/2022
 */

public class InteractSpeed extends Mode<Speed> {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 1, 10, 1);

    public InteractSpeed(String name, Speed parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<TickEvent> onTick = event -> {

        final int speed = this.speed.getValue().intValue();
        if (MoveUtil.isMoving()) {
            for (int i = 0; i < speed; i++) {
                PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem)));
                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));

                final double posX = mc.thePlayer.posX;
                final double posY = mc.thePlayer.posY;
                final double posZ = mc.thePlayer.posZ;

                mc.thePlayer.onLivingUpdate();

                mc.thePlayer.posX = posX;
                mc.thePlayer.posY = posY;
                mc.thePlayer.posZ = posZ;
            }
        }
    };
}