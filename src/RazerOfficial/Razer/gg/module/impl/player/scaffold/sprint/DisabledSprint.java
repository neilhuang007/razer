package RazerOfficial.Razer.gg.module.impl.player.scaffold.sprint;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.value.Mode;

public class DisabledSprint extends Mode<Scaffold> {

    public DisabledSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSprint.setPressed(false);
        mc.thePlayer.setSprinting(false);
    };
}
