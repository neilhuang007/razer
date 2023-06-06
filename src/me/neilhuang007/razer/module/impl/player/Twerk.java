package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;

@Rise
@ModuleInfo(name = "module.player.twerk.name", description = "module.player.twerk.description", category = Category.PLAYER)
public class Twerk extends Module {

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSneak.setPressed(Math.random() < 0.5 && mc.thePlayer.ticksExisted % 2 == 0);
    };


    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneakSlowDownMultiplier(0);
    };
}