package RazerOfficial.Razer.gg.module.impl.movement.jesus;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.BlockAABBEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Jesus;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class KarhuJesus extends Mode<Jesus> {

    public KarhuJesus(String name, Jesus parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        if (event.getBlock() instanceof BlockLiquid && !mc.gameSettings.keyBindSneak.isKeyDown()) {
            final int x = event.getBlockPos().getX();
            final int y = event.getBlockPos().getY();
            final int z = event.getBlockPos().getZ();

            event.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1));
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (PlayerUtil.onLiquid()) {
            event.setPosY(event.getPosY() - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.015625 : 0));
            event.setOnGround(false);
        }
    };
}