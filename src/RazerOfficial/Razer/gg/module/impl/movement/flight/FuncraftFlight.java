package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.MoveEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Wykt
 * @since 2/04/2023
 */

public final class FuncraftFlight extends Mode<Flight> {
    private final NumberValue speed = new NumberValue("Speed", this, 1.2, 0.8, 2, 0.05);
    private final BooleanValue vanillaKickBypass = new BooleanValue("Vanilla Kick Bypass", this, true);
    @EventLink
    private final Listener<PreMotionEvent> preMotionEventListener = event -> {
        event.setOnGround(true);
    };
    private double moveSpeed;
    private int stage, ticks;
    @EventLink(Priorities.VERY_HIGH)
    private final Listener<MoveEvent> moveEventListener = event -> {
        if (!MoveUtil.isMoving() || mc.thePlayer.isCollidedHorizontally) {
            stage = -1;
        }

        // vanilla kick bypass
        if (vanillaKickBypass.getValue() && ticks > 125) {
            stage = -1;
            ticks = 0;
            PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + 5, mc.thePlayer.posY + 1, mc.thePlayer.posZ + 5, true));
            return;
        }

        switch (stage) {
            case -1:
                mc.thePlayer.motionY = 0;
                event.setPosY(-0.00001);
                return;
            case 0:
                moveSpeed = 0.3;
                break;
            case 1:
                if (mc.thePlayer.onGround) {
                    event.setPosY(mc.thePlayer.motionY = 0.3999);
                    moveSpeed *= 2.14;
                }
                break;
            case 2:
                moveSpeed = speed.getValue().doubleValue();
                break;
            default:
                moveSpeed -= moveSpeed / 159;
                mc.thePlayer.motionY = 0;
                event.setPosY(-0.00001);
                break;
        }

        mc.thePlayer.jumpMovementFactor = 0F;
        MoveUtil.setMoveEvent(event, Math.max(moveSpeed, MoveUtil.getAllowedHorizontalDistance()));
        stage++;
    };

    public FuncraftFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        moveSpeed = 0;
        stage = mc.thePlayer.onGround ? 0 : -1;
        ticks = 0;
    }
}