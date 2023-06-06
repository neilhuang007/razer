package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class    NCPTower extends Mode<Scaffold> {

    public NCPTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2)) {
            PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));

//            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;

            if (mc.thePlayer.posY % 1 <= 0.00153598) {
                mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                mc.thePlayer.motionY = 0.42F;
            } else if (mc.thePlayer.posY % 1 < 0.1 && mc.thePlayer.offGroundTicks != 0) {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
            }
        }
    };
}
