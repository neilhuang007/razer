package me.neilhuang007.razer.module.impl.movement.wallclimb;

import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.impl.movement.WallClimb;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.SlotUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Alan
 * @since 22/3/2022
 */
public class WatchdogWallClimb extends Mode<WallClimb> {

    private boolean active;

    public WatchdogWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.onGround) {
                active = true;
            }

            if (active) {
                int block = SlotUtil.findBlock();

                if (block != -1) {
                    SlotComponent.setSlot(block, false);
                    if (SlotComponent.getItemIndex() == block) {
                        PacketUtil.send(new C08PacketPlayerBlockPlacement(new BlockPos(InstanceAccess.mc.thePlayer).down(), EnumFacing.UP.getIndex(), SlotComponent.getItemStack(), 0.0F, 1.0F, 0.0F));
                    }

                    InstanceAccess.mc.thePlayer.motionY = 0.42f;
                }
            }
        } else {
            active = false;
        }
    };
}