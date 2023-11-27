package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.component.impl.player.ItemDamageComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class MatrixFlight extends Mode<Flight> {

    private double verticalVelocity, horizontalVelocity;
    private int velocity;
    private boolean damage;
    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity s12PacketEntityVelocity = ((S12PacketEntityVelocity) packet);

            if (InstanceAccess.mc.thePlayer.getEntityId() == s12PacketEntityVelocity.getEntityID()) {
                final double velocityMotion = s12PacketEntityVelocity.motionY / 8000.0;

                if (velocityMotion > 0.2) {
                    this.verticalVelocity = velocityMotion;
                    this.horizontalVelocity = Math.sqrt(Math.abs(s12PacketEntityVelocity.motionX) / 8000f + Math.abs(s12PacketEntityVelocity.motionZ) / 8000f);
                    this.velocity = 0;

                    event.setCancelled(true);
                    damage = true;
                }
            }
        }
    };
    private ItemDamageComponent.Type type;
    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        velocity++;

        if (ItemDamageComponent.type == null) {
            return;
        }

        if (type == null) {
            type = ItemDamageComponent.type;
        }

        if (InstanceAccess.mc.thePlayer.onGround && damage) {
            onDisable();
        }

        if (type == ItemDamageComponent.Type.ROD) {
            if (this.damage) {
                final float speed = 1;

                InstanceAccess.mc.thePlayer.motionY = -1E-10D
                        + (InstanceAccess.mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                        - (InstanceAccess.mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);

                if (InstanceAccess.mc.thePlayer.getDistance(InstanceAccess.mc.thePlayer.lastReportedPosX, InstanceAccess.mc.thePlayer.lastReportedPosY, InstanceAccess.mc.thePlayer.lastReportedPosZ) <= 10 - speed - 0.15) {
                    event.setCancelled(true);
                }
            }
        } else {
            if (velocity <= 3) {
                final double yaw = Math.toRadians(InstanceAccess.mc.thePlayer.rotationYaw);
                final double speedBoost = (horizontalVelocity * 1.6) / 10 + 0.3;

                MoveUtil.strafe(this.horizontalVelocity);
                InstanceAccess.mc.thePlayer.motionY = verticalVelocity;
            }
        }
    };
    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (type == ItemDamageComponent.Type.ROD && damage) {
            final float speed = 1;

            event.setSpeed(speed);
        } else if (velocity >= 1000 || velocity <= 29) {
            event.setForward(0);
            event.setStrafe(0);
        }
    };

    public MatrixFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        this.velocity = 1000;
        ItemDamageComponent.damage(false);
        this.damage = false;
        this.type = null;
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}