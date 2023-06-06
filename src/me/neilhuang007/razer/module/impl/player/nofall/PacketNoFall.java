package me.neilhuang007.razer.module.impl.player.nofall;

import me.neilhuang007.razer.component.impl.player.FallDistanceComponent;
import me.neilhuang007.razer.module.impl.player.NoFall;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class PacketNoFall extends Mode<NoFall> {

    public PacketNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        float distance = FallDistanceComponent.distance;

        if (distance > 3) {
            PacketUtil.send(new C03PacketPlayer(true));
            distance = 0;
        }

        FallDistanceComponent.distance = distance;
    };
}