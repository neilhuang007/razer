package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.other.BlockDamageEvent;
import me.neilhuang007.razer.util.player.SlotUtil;
import net.minecraft.util.BlockPos;

/**
 * @author Alan (made good code)
 * @since 24/06/2023
 */

@Rise
@ModuleInfo(name = "module.player.autotool.name", description = "module.player.autotool.description", category = Category.PLAYER)
public class AutoTool extends Module {

    private int slot, lastSlot = -1;
    private int blockBreak;
    private BlockPos blockPos;

    @EventLink(Priorities.VERY_HIGH)
    public final Listener<BlockDamageEvent> onBlockDamage = event -> {
        blockBreak = 3;
        blockPos = event.getBlockPos();
    };

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        switch (mc.objectMouseOver.typeOfHit) {
            case BLOCK:
                if (blockPos != null && blockBreak > 0) {
                    slot = SlotUtil.findTool(blockPos);
                } else {
                    slot = -1;
                }
                break;

            case ENTITY:
                slot = SlotUtil.findSword();
                break;

            default:
                slot = -1;
                break;
        }

        if (lastSlot != -1) {
            SlotComponent.setSlot(lastSlot);
        } else if (slot != -1) {
            SlotComponent.setSlot(slot);
        }

        lastSlot = slot;
        blockBreak--;
    };

}