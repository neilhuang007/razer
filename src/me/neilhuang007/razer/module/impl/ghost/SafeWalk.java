package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.item.ItemBlock;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.safewalk.name", description = "module.ghost.safewalk.description", category = Category.GHOST)
public class SafeWalk extends Module {

    private final BooleanValue blocksOnly = new BooleanValue("Blocks Only", this, false);
    private final BooleanValue backwardsOnly = new BooleanValue("Backwards Only", this, false);

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        mc.thePlayer.safeWalk = mc.thePlayer.onGround && (!mc.gameSettings.keyBindForward.isKeyDown() || !backwardsOnly.getValue()) &&
                ((SlotComponent.getItemStack() != null && SlotComponent.getItemStack().getItem() instanceof ItemBlock) ||
                        !this.blocksOnly.getValue());
    };
}