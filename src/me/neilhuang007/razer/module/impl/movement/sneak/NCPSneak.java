package me.neilhuang007.razer.module.impl.movement.sneak;

import me.neilhuang007.razer.module.impl.movement.Sneak;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PostMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C0BPacketEntityAction;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class NCPSneak extends Mode<Sneak> {

    public NCPSneak(String name, Sneak parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;

        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    };

    @Override
    public void onDisable() {
        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
}