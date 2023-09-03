package RazerOfficial.Razer.gg.module.impl.other;


import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;

@Rise
@ModuleInfo(name = "tickevent toggled", description = "is tick event toggled", category = Category.OTHER)
public class TickEventToggled extends Module {
    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        System.out.println("tick event just occured");
    };
}
