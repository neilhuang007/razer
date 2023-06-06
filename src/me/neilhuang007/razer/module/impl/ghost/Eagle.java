package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;

/**
 * @author Auth
 * @since 27/4/2022
 */
@Rise
@ModuleInfo(name = "module.ghost.legitscaffold.name", description = "module.ghost.eagle.description", category = Category.GHOST)
public class Eagle extends Module {

    private final NumberValue slow = new NumberValue("Sneak speed multiplier", this, 0.3, 0.2, 1, 0.05);
    private final BooleanValue groundOnly = new BooleanValue("Only on ground", this, false);
    private final BooleanValue blocksOnly = new BooleanValue("Only when holding blocks", this, false);
    private final BooleanValue backwardsOnly = new BooleanValue("Only when moving backwards", this, false);
    private final BooleanValue onlyOnSneak = new BooleanValue("Only on Sneak", this, true);

    private boolean sneaked;
    private int ticksOverEdge;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) &&
                blocksOnly.getValue()) {
            if (sneaked) {
                sneaked = false;
            }
            return;
        }

        if ((mc.thePlayer.onGround || !groundOnly.getValue()) &&
                (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir) &&
                (!mc.gameSettings.keyBindForward.isKeyDown() || !backwardsOnly.getValue())) {
            if (!sneaked) {
                sneaked = true;
            }
        } else if (sneaked) {
            sneaked = false;
        }

        if (sneaked) {
            mc.gameSettings.keyBindSprint.setPressed(false);
        }

        if (sneaked) {
            ticksOverEdge++;
        } else {
            ticksOverEdge = 0;
        }
    };

    @Override
    protected void onDisable() {
        if (sneaked) {
            sneaked = false;
        }
    }

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak((sneaked && (mc.gameSettings.keyBindSneak.isKeyDown() || !onlyOnSneak.getValue())) ||
                (mc.gameSettings.keyBindSneak.isKeyDown() && !onlyOnSneak.getValue()));

        if (sneaked && ticksOverEdge <= 2) {
            event.setSneakSlowDownMultiplier(slow.getValue().doubleValue());
        }
    };
}