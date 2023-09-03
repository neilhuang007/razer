package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.player.MoveUtil;

/**
 * @author Alan Jr.
 * @since 9/17/2022
 */
@Rise
@ModuleInfo(name = "module.movement.strafe.name", description = "module.movement.strafe.description", category = Category.MOVEMENT)
public class Strafe extends Module {

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0F;
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.strafe();
    };
}