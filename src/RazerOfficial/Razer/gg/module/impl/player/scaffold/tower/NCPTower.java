package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
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
