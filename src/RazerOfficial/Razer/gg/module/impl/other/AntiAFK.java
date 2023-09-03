package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import net.minecraft.client.settings.GameSettings;

@Rise
@ModuleInfo(name = "module.other.antiafk.name", description = "module.other.antiafk.description", category = Category.OTHER)
public final class AntiAFK extends Module {

    private int lastInput;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        GameSettings gameSettings = mc.gameSettings;
        if (gameSettings.keyBindJump.isKeyDown() ||
                gameSettings.keyBindRight.isKeyDown() ||
                gameSettings.keyBindForward.isKeyDown() ||
                gameSettings.keyBindLeft.isKeyDown() ||
                gameSettings.keyBindBack.isKeyDown()) {
            lastInput = 0;
        }

        lastInput++;

        if (lastInput < 20 * 10) return;

        if (mc.thePlayer.ticksExisted % 5 == 0) {
            mc.gameSettings.keyBindRight.setPressed(false);
            mc.gameSettings.keyBindLeft.setPressed(false);
            mc.gameSettings.keyBindJump.setPressed(false);
        }

        if (mc.thePlayer.ticksExisted % 20 == 0) {
            if (mc.thePlayer.ticksExisted % 40 == 0) {
                mc.gameSettings.keyBindRight.setPressed(true);
            } else {
                mc.gameSettings.keyBindLeft.setPressed(true);
            }
        }

        if (mc.thePlayer.ticksExisted % 100 == 0) {
            mc.gameSettings.keyBindJump.setPressed(true);
        }
    };
}
