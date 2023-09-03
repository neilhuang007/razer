package RazerOfficial.Razer.gg.module.impl.movement.sneak;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PostMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Sneak;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
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