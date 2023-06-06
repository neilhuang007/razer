package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.math.MathUtil;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;

@Rise
@ModuleInfo(name = "module.other.timer.name", description = "module.other.timer.description", category = Category.OTHER)
public final class Timer extends Module {

    private final BoundsNumberValue timer =
            new BoundsNumberValue("Timer", this, 1, 2, 0.1, 10, 0.05);

    @EventLink(value = Priorities.MEDIUM)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.timer.timerSpeed = (float) MathUtil.getRandom(timer.getValue().floatValue(), timer.getSecondValue().floatValue());
    };
}
