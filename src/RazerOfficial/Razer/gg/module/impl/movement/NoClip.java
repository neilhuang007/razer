package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PushOutOfBlockEvent;
import RazerOfficial.Razer.gg.event.impl.other.BlockAABBEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.util.player.SlotUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

@Rise
@ModuleInfo(name = "module.movement.noclip.name", description = "module.movement.noclip.description", category = Category.MOVEMENT)
public class NoClip extends Module {

    private final BooleanValue block = new BooleanValue("Block", this, false);

    @Override
    protected void onDisable() {
        InstanceAccess.mc.thePlayer.noClip = false;
    }

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        if (PlayerUtil.insideBlock()) {
            event.setBoundingBox(null);

            // Sets The Bounding Box To The Players Y Position.
            if (!(event.getBlock() instanceof BlockAir) && !InstanceAccess.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

                if (y < InstanceAccess.mc.thePlayer.posY) {
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

        InstanceAccess.mc.thePlayer.noClip = true;

        if (block.getValue()) {
            final int slot = SlotUtil.findBlock();

            if (slot == -1) {
                return;
            }

            SlotComponent.setSlot(slot, true);

            RotationComponent.setRotations(new Vector2f(InstanceAccess.mc.thePlayer.rotationYaw, 90), 2 + Math.random(), MovementFix.NORMAL);

            if (RotationComponent.rotations.y >= 89 &&
                    InstanceAccess.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                    InstanceAccess.mc.thePlayer.posY == InstanceAccess.mc.objectMouseOver.getBlockPos().up().getY()) {

                InstanceAccess.mc.playerController.onPlayerRightClick(InstanceAccess.mc.thePlayer, InstanceAccess.mc.theWorld, SlotComponent.getItemStack(),
                        InstanceAccess.mc.objectMouseOver.getBlockPos(), InstanceAccess.mc.objectMouseOver.sideHit, InstanceAccess.mc.objectMouseOver.hitVec);

                InstanceAccess.mc.thePlayer.swingItem();
            }
        }
    };
}