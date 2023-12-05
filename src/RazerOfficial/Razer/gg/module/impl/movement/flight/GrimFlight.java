package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.player.TeleportEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.rotation.RotationUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.util.vector.Vector3d;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

/**
 * @author Alan
 * @since 03.07.2022
 */
public class GrimFlight extends Mode<Flight> {

    @EventLink
    private Listener<PreMotionEvent> preMotion = event -> {
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            // Creating a variable that gets the block that the user is looking at and creating another variable with incremented Y position of the position so that the user teleports on top of the block.
            MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTrace(999, 1);
            if (movingObjectPosition == null) return;

            final BlockPos pos = movingObjectPosition.getBlockPos();
            final BlockPos tpPos = pos.offset(movingObjectPosition.sideHit, 4);

            Vector2f rotations = RotationUtil.calculate(
                    new Vector3d(tpPos.getX(), tpPos.getY(), tpPos.getZ()),
                    new Vector3d(pos.getX(), pos.getY(), pos.getZ()));
            PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(tpPos.getX(), tpPos.getY() - 1, tpPos.getZ(), rotations.x, rotations.y, false));

            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, SlotComponent.getItemStack(),
                    movingObjectPosition.getBlockPos(), movingObjectPosition.sideHit, movingObjectPosition.hitVec);
        }
    };
    @EventLink
    private Listener<TeleportEvent> teleport = event -> {
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) event.setCancelled(true);
    };

    public GrimFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("Any blocks you place will be ghost blocks.");
    }

    @Override
    public void onDisable() {

    }
}
