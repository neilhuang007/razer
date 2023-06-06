package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.MoveInputEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.BlockAABBEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Nicklas
 * @since 19.08.2022
 */

public class AirJumpFlight extends Mode<Flight> {
    private double y;

    public AirJumpFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        y = Math.floor(mc.thePlayer.posY);
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
            y = mc.thePlayer.posY;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };


    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() && (mc.thePlayer.posY < y + 1 || mc.gameSettings.keyBindJump.isKeyDown())) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(
                        -15,
                        -1,
                        -15,
                        15,
                        1,
                        15
                ).offset(x, y, z));
            }
        }
    };


    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setSneak(false);
    };
}