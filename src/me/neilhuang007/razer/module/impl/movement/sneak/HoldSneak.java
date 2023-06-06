package me.neilhuang007.razer.module.impl.movement.sneak;

import me.neilhuang007.razer.module.impl.movement.Sneak;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;
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