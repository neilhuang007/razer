package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;

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