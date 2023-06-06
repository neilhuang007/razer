package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.BadPacketsComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.impl.NumberValue;

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