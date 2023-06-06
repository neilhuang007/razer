package me.neilhuang007.razer.newevent.impl.other;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import net.minecraft.network.play.client.C03PacketPlayer;

@Getter
@Setter
@AllArgsConstructor
public final class TeleportEvent extends CancellableEvent {

    private C03PacketPlayer response;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;

}