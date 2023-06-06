package me.neilhuang007.razer.module.impl.movement.noslow;

import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.impl.movement.NoSlow;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PostMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.SlowDownEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class NCPNoSlow extends Mode<NoSlow> {

    public NCPNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (InstanceAccess.mc.thePlayer.isBlocking()) {
            PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (InstanceAccess.mc.thePlayer.isBlocking()) {
            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };
}