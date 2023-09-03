package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;

public class VanillaTower extends Mode<Scaffold> {

    public VanillaTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (InstanceAccess.mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2)) {
            InstanceAccess.mc.thePlayer.motionY = 0.42F;
        }
    };
}
