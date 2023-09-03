package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.RayCastUtil;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.util.player.SlotUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class ClutchNoFall extends Mode<NoFall> {

    private C08PacketPlayerBlockPlacement placePacket;

    public ClutchNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (mc.thePlayer.fallDistance > 3 && this.placePacket == null && PlayerUtil.isBlockUnder(15)) {
            final int slot = SlotUtil.findItem(Items.water_bucket);

            if (slot == -1) {
                return;
            }

            SlotComponent.setSlot(slot);

            final double minRotationSpeed = 8;
            final double maxRotationSpeed = 10;
            final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);
            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), rotationSpeed, MovementFix.NORMAL);

            if (RotationComponent.rotations.y > 85 && !BadPacketsComponent.bad()) {
                for (int i = 0; i < 3; i++) {
                    final Block block = PlayerUtil.blockRelativeToPlayer(0, -i, 0);

                    if (block.getMaterial() == Material.water) {
                        break;
                    }

                    if (block.isFullBlock()) {
                        final BlockPos position = new BlockPos(mc.thePlayer).down(i);

                        Vec3 hitVec = new Vec3(position.getX() + Math.random(), position.getY() + Math.random(), position.getZ() + Math.random());
                        final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationComponent.rotations, mc.playerController.getBlockReachDistance());
                        if (movingObjectPosition != null && movingObjectPosition.getBlockPos().equals(position)) {
                            hitVec = movingObjectPosition.hitVec;
                        }

                        final float f = (float) (hitVec.xCoord - (double) position.getX());
                        final float f1 = 1.0F;
                        final float f2 = (float) (hitVec.zCoord - (double) position.getZ());

                        PacketUtil.send(this.placePacket = new C08PacketPlayerBlockPlacement(position, EnumFacing.UP.getIndex(), SlotComponent.getItemStack(), f, f1, f2));
                        PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                        break;
                    }
                }
            }
        } else if (this.placePacket != null && mc.thePlayer.onGroundTicks > 1) {
            int slot = SlotUtil.findItem(Items.bucket);

            if (slot == -1) {
                slot = SlotUtil.findItem(Items.water_bucket);
            }

            if (slot == -1) {
                this.placePacket = null;
                return;
            }

            SlotComponent.setSlot(slot);

            final double minRotationSpeed = 8;
            final double maxRotationSpeed = 10;
            final float rotationSpeed = (float) MathUtil.getRandom(minRotationSpeed, maxRotationSpeed);
            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), rotationSpeed, MovementFix.NORMAL);

            if (RotationComponent.rotations.y > 85 && !BadPacketsComponent.bad()) {
                PacketUtil.send(this.placePacket);
                PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
            }

            this.placePacket = null;
        }
    };
}