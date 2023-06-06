package me.neilhuang007.razer.module.impl.player.nofall;

import me.neilhuang007.razer.component.impl.player.FallDistanceComponent;
import me.neilhuang007.razer.module.impl.player.NoFall;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
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
        if (FallDistanceComponent.distance > 3.5 && !(PlayerUtil.blockRelativeToPlayer(0, MoveUtil.predictedMotion(mc.thePlayer.motionY), 0) instanceof BlockAir) && mc.thePlayer.ticksSinceTeleport > 50) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 50 - Math.random(), mc.thePlayer.posZ, false));

            FallDistanceComponent.distance = 0;
        }
    };
}