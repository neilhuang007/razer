package RazerOfficial.Razer.gg.module.impl.movement.step;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.StepEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Step;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class VulcanStep extends Mode<Step> {

    public VulcanStep(String name, Step parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        InstanceAccess.mc.thePlayer.stepHeight = 0.6F;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.ticksSinceJump > 11) {
            InstanceAccess.mc.thePlayer.stepHeight = 1;
        } else {
            InstanceAccess.mc.thePlayer.stepHeight = 0.6F;
        }
    };

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {

        if (event.getHeight() > 0.6) {
            InstanceAccess.mc.timer.timerSpeed = 0.5f;
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY + 0.5F, InstanceAccess.mc.thePlayer.posZ, true));
        }
    };
}