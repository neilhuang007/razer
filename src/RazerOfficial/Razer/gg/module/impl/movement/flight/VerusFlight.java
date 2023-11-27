package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.BlockAABBEvent;
import RazerOfficial.Razer.gg.event.impl.other.MoveEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author Nicklas
 * @since 31.03.2022
 */

public class VerusFlight extends Mode<Flight> { // TODO: make sneaking go down

    @EventLink()
    public final Listener<MoveInputEvent> onMoveInput = event -> {

        // Sets Sneaking To False So That We Can't Sneak When Flying Because That Can Cause Flags.
        event.setSneak(false);
    };
    // Sub Modes.
    private final ModeValue mode = new ModeValue("Sub-Mode", this)
            .add(new SubMode("Fast"))
            .setDefault("Fast");
    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {

        switch (mode.getValue().getName()) {
            case "Fast": {
                // Sets The Bounding Box To The Players Y Position.
                if (event.getBlock() instanceof BlockAir && !mc.gameSettings.keyBindSneak.isKeyDown() || mc.gameSettings.keyBindJump.isKeyDown()) {
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
                break;
            }
        }
    };
    private int ticks = 0;
    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        switch (mode.getValue().getName()) {
            case "Fast": {
                // When U Press Space U Go Up By 0.42F Every 2 Ticks.
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.motionY = 0.42F;
                    }
                }

                break;
            }
        }

        ++ticks;
    };

    @EventLink()
    public final Listener<MoveEvent> onMove = event -> {

        if (mode.getValue().getName().equals("Fast")) {
            // Sets Y To 0.42F Every 14 ticks & When OnGround To Bypass Fly 4A.
            if (mc.thePlayer.onGround && ticks % 14 == 0) {
                event.setPosY(0.42F);
                MoveUtil.strafe(0.69);
                mc.thePlayer.motionY = -(mc.thePlayer.posY - Math.floor(mc.thePlayer.posY));
            } else {
                // A Slight Speed Boost.
                if (mc.thePlayer.onGround) {
                    MoveUtil.strafe(1.01 + MoveUtil.speedPotionAmp(0.15));
                    // Slows Down To Not Flag Speed11A.
                } else MoveUtil.strafe(0.41 + MoveUtil.speedPotionAmp(0.05));
            }

            mc.thePlayer.setSprinting(true);
            mc.thePlayer.omniSprint = true;
        }

        ticks++;
    };

    public VerusFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}