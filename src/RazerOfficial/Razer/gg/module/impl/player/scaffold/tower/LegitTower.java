package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.value.Mode;

public class LegitTower extends Mode<Scaffold> {
    // Bypasses jump delay, holding down space is slower than this
    public LegitTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}
