package RazerOfficial.Razer.gg.module.impl.movement.jesus;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.BlockAABBEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Jesus;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Alan
 * @since 16.05.2022
 */

public class VanillaJesus extends Mode<Jesus> {

    public VanillaJesus(String name, Jesus parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (event.getBlock() instanceof BlockLiquid && !InstanceAccess.mc.gameSettings.keyBindSneak.isKeyDown()) {
            final int x = event.getBlockPos().getX();
            final int y = event.getBlockPos().getY();
            final int z = event.getBlockPos().getZ();

            event.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1));
        }
    };
}