package RazerOfficial.Razer.gg.component.impl.patches;

import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
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
