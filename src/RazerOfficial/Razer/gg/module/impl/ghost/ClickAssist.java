package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.clickassist.name", description = "module.ghost.clickassist.description", category = Category.GHOST)
public class ClickAssist extends Module {

    public final NumberValue extraLeftClicks = new NumberValue("Extra Left Clicks", this, 1, 0, 3, 1);
    public final NumberValue extraRightClicks = new NumberValue("Extra Right Clicks", this, 1, 0, 3, 1);

    public int leftClicks, rightClicks;
    private boolean leftClick, rightClick;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            if (!leftClick) {
                leftClicks = extraLeftClicks.getValue().intValue();
            }

            leftClick = true;
        } else {
            leftClick = false;
        }

        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (!rightClick) {
                rightClicks = extraRightClicks.getValue().intValue();
            }

            rightClick = true;
        } else {
            rightClick = false;
        }

        if (leftClicks > 0 && Math.random() > 0.2) {
            leftClicks--;

            if (!mc.thePlayer.isUsingItem() && !BadPacketsComponent.bad()) {
                mc.clickMouse();
            }
        } else if (rightClicks > 0 && Math.random() > 0.2) {
            rightClicks--;

            if (!mc.thePlayer.isUsingItem() && !BadPacketsComponent.bad()) {
                mc.rightClickMouse();
            }
        }
    };
}