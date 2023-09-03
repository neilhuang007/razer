package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
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