package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import util.time.StopWatch;


@Rise
@ModuleInfo(name = "test", description = "timer", category = Category.OTHER)
public class Test extends Module {
    StopWatch stopWatch = new StopWatch();

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if(stopWatch.finished(5000)){
            ChatUtil.display("finished 5000ms delay");
            stopWatch.reset();
        }
    };


}
