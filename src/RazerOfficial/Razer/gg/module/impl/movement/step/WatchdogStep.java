package RazerOfficial.Razer.gg.module.impl.movement.step;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.StepEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Step;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 22/3/2022
 */

public class WatchdogStep extends Mode<Step> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 1.5, 0.1);
    private final NumberValue timer = new NumberValue("Timer", this, 0.5, 0.1, 1, 0.1);

    public WatchdogStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround && !PlayerUtil.inLiquid()) {
            mc.thePlayer.stepHeight = this.height.getValue().floatValue();
        } else {
            mc.thePlayer.stepHeight = 0.6F;
        }
    };

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {
        if (!mc.thePlayer.onGround || PlayerUtil.inLiquid()) {
            return;
        }

        final double height = event.getHeight();

        if (height <= 0.6F) {
            return;
        }

        final double[] values;

        if (height > 1.015) {
            values = new double[]{0.42F, 0.7532F, 1.0F, 0.98F};
        } else if (height > 0.875) {
            values = new double[]{0.42F, 0.7532F, 1.0F};
        } else {
            values = new double[]{0.39F, 0.6938F};
        }

        mc.timer.timerSpeed = this.timer.getValue().floatValue();

        for (final double d : values) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ, false));
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }
}