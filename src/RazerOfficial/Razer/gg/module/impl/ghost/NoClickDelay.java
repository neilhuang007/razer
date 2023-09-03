package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;

/**
 * @author Alan Jr. (Not Billionaire)
 * @since 19/9/2022
 */
@Rise
@ModuleInfo(name = "module.ghost.noclickdelay.name", description = "module.ghost.noclickdelay.description", category = Category.GHOST)
public class NoClickDelay extends Module {

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (InstanceAccess.mc.thePlayer != null && InstanceAccess.mc.theWorld != null) {
            InstanceAccess.mc.leftClickCounter = 0;
        }
    };
}