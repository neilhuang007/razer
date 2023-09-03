package RazerOfficial.Razer.gg.module.impl.movement.sneak;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Sneak;
import RazerOfficial.Razer.gg.value.Mode;
import org.lwjgl.input.Keyboard;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class HoldSneak extends Mode<Sneak> {

    public HoldSneak(String name, Sneak parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.gameSettings.keyBindSneak.setPressed(true);
    };

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSneak.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
    }
}