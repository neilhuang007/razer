package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.RotationComponent;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.component.impl.player.rotationcomponent.MovementFix;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.motion.PushOutOfBlockEvent;
import me.neilhuang007.razer.newevent.impl.other.BlockAABBEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.util.player.SlotUtil;
import me.neilhuang007.razer.util.vector.Vector2f;
import me.neilhuang007.razer.value.impl.BooleanValue;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

@Rise
@ModuleInfo(name = "module.movement.noclip.name", description = "module.movement.noclip.description", category = Category.MOVEMENT)
public class NoClip extends Module {

    private final BooleanValue block = new BooleanValue("Block", this, false);

    @Override
    protected void onDisable() {
        mc.thePlayer.noClip = false;
    }

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        if (PlayerUtil.insideBlock()) {
            event.setBoundingBox(null);

            // Sets The Bounding Box To The Players Y Position.
            if (!(event.getBlock() instanceof BlockAir) && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }
        }
    };

    @EventLink()
    public final Listener<PushOutOfBlockEvent> onPushOutOfBlock = event -> {
        event.setCancelled(true);
    };

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        mc.thePlayer.noClip = true;

        if (block.getValue()) {
            final int slot = SlotUtil.findBlock();

            if (slot == -1) {
                return;
            }

            SlotComponent.setSlot(slot, true);

            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), 2 + Math.random(), MovementFix.NORMAL);

            if (RotationComponent.rotations.y >= 89 &&
                    mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                    mc.thePlayer.posY == mc.objectMouseOver.getBlockPos().up().getY()) {

                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, SlotComponent.getItemStack(),
                        mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit, mc.objectMouseOver.hitVec);

                mc.thePlayer.swingItem();
            }
        }
    };
}