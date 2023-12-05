package RazerOfficial.Razer.gg.module.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.player.BlockDamageEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.SlotUtil;
import net.minecraft.util.BlockPos;

/**
 * @author Alan (made good code)
 * @since 24/06/2023
 */

@Razer
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
        switch (InstanceAccess.mc.objectMouseOver.typeOfHit) {
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