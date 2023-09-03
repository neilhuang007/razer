package RazerOfficial.Razer.gg.module.impl.movement.step;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.StepEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Step;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 22/3/2022
 */

public class NCPStep extends Mode<Step> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 2.5, 0.1);
    private final NumberValue timer = new NumberValue("Timer", this, 0.5, 0.1, 1, 0.1);
    private final BooleanValue reverse = new BooleanValue("Reverse", this, false);

    public NCPStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround && !PlayerUtil.inLiquid()) {
            mc.thePlayer.stepHeight = this.height.getValue().floatValue();
        } else {
            mc.thePlayer.stepHeight = 0.6F;
        }

        if (!reverse.getValue() || PlayerUtil.blockRelativeToPlayer(0, -(this.height.getValue().intValue() + 1), 0) instanceof BlockAir || PlayerUtil.inLiquid()) {
            return;
        }

        for (int i = 1; i < this.height.getValue().intValue() + 1; i++) {
            mc.thePlayer.motionY -= i;
        }
    };

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {

        if (!mc.thePlayer.onGround || PlayerUtil.inLiquid()) {
            return;
        }

        final double height = event.getHeight();

        if (height <= 0.6) {
            return;
        }

        final double[] values;

        if (height > 2.019) {
            values = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.919};
        } else if (height > 1.869) {
            values = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
        } else if (height > 1.5) {
            values = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652};
        } else if (height > 1.015) {
            values = new double[]{0.42, 0.7532, 1.01, 1.093, 1.015};
        } else if (height > 0.875) {
            values = new double[]{0.42, 0.7532};
        } else {
            values = new double[]{0.39, 0.6938};
        }

        mc.timer.timerSpeed = this.timer.getValue().floatValue();

        for (final double d : values) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + (d + (Math.random() / 2000)), mc.thePlayer.posZ, false));
        }

    };

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }
}