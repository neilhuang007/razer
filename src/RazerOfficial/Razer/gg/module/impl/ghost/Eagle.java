package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;

/**
 * @author Auth
 * @since 27/4/2022
 */
@Razer
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