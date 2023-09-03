package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 3/02/2022
 */
public class WatchdogNoFall extends Mode<NoFall> {
    boolean active;

    public WatchdogNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (FallDistanceComponent.distance > 3.5 && !(PlayerUtil.blockRelativeToPlayer(0, MoveUtil.predictedMotion(InstanceAccess.mc.thePlayer.motionY), 0) instanceof BlockAir) && InstanceAccess.mc.thePlayer.ticksSinceTeleport > 50) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY + 50 - Math.random(), InstanceAccess.mc.thePlayer.posZ, false));

            FallDistanceComponent.distance = 0;
        }
    };
}