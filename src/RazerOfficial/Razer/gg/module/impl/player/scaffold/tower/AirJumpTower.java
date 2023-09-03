package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;

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
