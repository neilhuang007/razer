package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;

public class AirJumpTower extends Mode<Scaffold> {

    public AirJumpTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.ticksExisted % 2 == 0 && PlayerUtil.blockNear(2)) {
            mc.thePlayer.motionY = 0.42F;
            event.setOnGround(true);
        }
    };
}
