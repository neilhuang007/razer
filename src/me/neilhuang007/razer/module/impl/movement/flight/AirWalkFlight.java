package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.BlockAABBEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public final class AirWalkFlight extends Mode<Flight> {

    public AirWalkFlight(String name, Flight parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        // Sets The Bounding Box To The Players Y Position.
        if (event.getBlock() instanceof BlockAir && !InstanceAccess.mc.thePlayer.isSneaking()) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < InstanceAccess.mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }
    };
}
