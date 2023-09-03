package RazerOfficial.Razer.gg.module.impl.render;


import RazerOfficial.Razer.gg.event.CancellableEvent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.StrafeEvent;
import RazerOfficial.Razer.gg.event.impl.other.BlockAABBEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketSendEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.util.vector.Vector3d;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

@Getter
@ModuleInfo(name = "module.render.freecam.name", description = "module.render.freecam.description", category = Category.RENDER)
public final class FreeCam extends Module {

    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);
    private Vector3d position, delta;
    private Vector2f rotation;
    private boolean sprinting;

    @Override
    protected void onEnable() {
        position = new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        delta = new Vector3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        rotation = new Vector2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        sprinting = mc.gameSettings.keyBindSprint.isKeyDown();
    }

    @Override
    protected void onDisable() {
        mc.thePlayer.setPosition(position.getX(), position.getY(), position.getZ());
        mc.thePlayer.rotationYaw = rotation.getX();
        mc.thePlayer.rotationPitch = rotation.getY();
        mc.thePlayer.motionX = delta.getX();
        mc.thePlayer.motionY = delta.getY();
        mc.thePlayer.motionZ = delta.getZ();
        mc.gameSettings.keyBindSprint.setPressed(sprinting);
    }

    @EventLink
    public final Listener<BlockAABBEvent> blockAABBEventListener = CancellableEvent::setCancelled;

    @EventLink
    public final Listener<PacketSendEvent> send = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof C0APacketAnimation || packet instanceof C03PacketPlayer ||
                packet instanceof C02PacketUseEntity || packet instanceof C0BPacketEntityAction ||
                packet instanceof C08PacketPlayerBlockPlacement) {
            event.setCancelled();
        }
    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final float speed = this.speed.getValue().floatValue();

        event.setSpeed(speed);
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        final float speed = this.speed.getValue().floatValue();

        mc.thePlayer.motionY = 0.0D
                + (mc.gameSettings.keyBindJump.isKeyDown() ? speed : 0.0D)
                - (mc.gameSettings.keyBindSneak.isKeyDown() ? speed : 0.0D);
    };


    @EventLink()
    public final Listener<MoveInputEvent> onMovementInput = event -> {
        event.setSneak(false);
    };
}