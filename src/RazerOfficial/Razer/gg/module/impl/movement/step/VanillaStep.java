package RazerOfficial.Razer.gg.module.impl.movement.step;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.StepEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Step;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

/**
 * @author Auth
 * @since 22/3/2022
 */

public class VanillaStep extends Mode<Step> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 10, 0.1);
    private final BooleanValue reverse = new BooleanValue("Reverse", this, false);
    private final NumberValue timer = new NumberValue("Timer", this, 0.5, 0.1, 1, 0.1);

    public VanillaStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        InstanceAccess.mc.thePlayer.stepHeight = this.height.getValue().floatValue();

        if (!reverse.getValue() || !PlayerUtil.isBlockUnder(height.getValue().floatValue() + InstanceAccess.mc.thePlayer.getEyeHeight()) || PlayerUtil.inLiquid()) {
            return;
        }

        if (InstanceAccess.mc.thePlayer.posY < InstanceAccess.mc.thePlayer.lastGroundY) {
            if (!InstanceAccess.mc.thePlayer.onGround && InstanceAccess.mc.thePlayer.offGroundTicks <= 1) {
                InstanceAccess.mc.thePlayer.motionY = -height.getValue().doubleValue();
            }
        }

        if (InstanceAccess.mc.thePlayer.offGroundTicks == 1 && InstanceAccess.mc.thePlayer.posY < InstanceAccess.mc.thePlayer.lastLastGroundY) {
            InstanceAccess.mc.timer.timerSpeed = timer.getValue().floatValue();
        }
    };

    @Override
    public void onDisable() {
        InstanceAccess.mc.thePlayer.stepHeight = 0.6F;
    }

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {

        if (event.getHeight() > 0.6) {
            InstanceAccess.mc.timer.timerSpeed = timer.getValue().floatValue();
        }
    };
}