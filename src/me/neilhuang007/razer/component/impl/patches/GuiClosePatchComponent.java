package me.neilhuang007.razer.component.impl.patches;

import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiClosePatchComponent extends Component {

    private boolean inGUI;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.currentScreen == null && inGUI) {
            for (final KeyBinding bind : mc.gameSettings.keyBindings) {
                bind.setPressed(GameSettings.isKeyDown(bind));
            }
        }

        inGUI = mc.currentScreen != null;
    };
}
