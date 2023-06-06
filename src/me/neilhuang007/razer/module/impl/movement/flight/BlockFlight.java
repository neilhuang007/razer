package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.component.impl.player.RotationComponent;
import me.neilhuang007.razer.component.impl.player.rotationcomponent.MovementFix;
import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.util.vector.Vector2f;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Alan
 * @since 31.03.2022
 */

public class BlockFlight extends Mode<Flight> {

    public BlockFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), 100, MovementFix.OFF);
        if (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir) {
            PacketUtil.send(new C0APacketAnimation());

            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                    mc.thePlayer.getCurrentEquippedItem(),
                    new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ),
                    EnumFacing.UP, mc.objectMouseOver.hitVec);
        }
    };
}