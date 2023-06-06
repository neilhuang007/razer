package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.Type;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;

/**
 * @author Auth
 * @since 20/10/2021
 */
@ModuleInfo(name = "module.movement.sprint.name", description = "module.movement.sprint.description", category = Category.MOVEMENT)
public class Sprint extends Module {
    private final BooleanValue legit = new BooleanValue("Legit", this, true, () -> Client.CLIENT_TYPE != Type.RISE);

    @EventLink(value = Priorities.LOW)
    public final Listener<StrafeEvent> onStrafe = event -> {

        mc.gameSettings.keyBindSprint.setPressed(true);

        if (Client.CLIENT_TYPE != Type.RISE) return;

        if (mc.thePlayer.omniSprint && MoveUtil.isMoving() && !legit.getValue()) {
            mc.thePlayer.setSprinting(true);
        }

        mc.thePlayer.omniSprint = !legit.getValue() && MoveUtil.isMoving() && !mc.thePlayer.isCollidedHorizontally &&
                !mc.thePlayer.isSneaking() && !mc.thePlayer.isUsingItem();
    };

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
        mc.thePlayer.omniSprint = false;
    }
}