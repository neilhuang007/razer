package me.neilhuang007.razer.module.impl.movement.jesus;


import me.neilhuang007.razer.module.impl.movement.Jesus;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.BlockAABBEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class NCPJesus extends Mode<Jesus> {

    public NCPJesus(String name, Jesus parent) {
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
        if (mc.thePlayer.ticksExisted % 2 == 0 && PlayerUtil.onLiquid()) {
            event.setPosY(event.getPosY() - 0.015625);
        }
    };
}