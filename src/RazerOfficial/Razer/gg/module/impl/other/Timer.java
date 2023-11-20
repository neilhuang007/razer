package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;

@Razer
@ModuleInfo(name = "module.other.timer.name", description = "module.other.timer.description", category = Category.OTHER)
public final class Timer extends Module {

    private final BoundsNumberValue timer =
            new BoundsNumberValue("Timer", this, 1, 2, 0.1, 10, 0.05);

    @EventLink(value = Priorities.MEDIUM)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.timer.timerSpeed = (float) MathUtil.getRandom(timer.getValue().floatValue(), timer.getSecondValue().floatValue());
    };
}
