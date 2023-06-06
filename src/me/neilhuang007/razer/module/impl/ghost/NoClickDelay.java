package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

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