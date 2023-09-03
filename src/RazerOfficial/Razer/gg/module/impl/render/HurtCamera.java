package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MathHelper;

/**
 * @author Alan
 * @since 28/05/2022
 */

// EntityRenderer.java 634
@ModuleInfo(name = "module.render.hurtcamera.name", description = "module.render.hurtcamera.description", category = Category.RENDER)
public final class HurtCamera extends Module {

    public final NumberValue intensity = new NumberValue("Intensity", this, 1, 0, 1, 0.1);


    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = ((S12PacketEntityVelocity) packet);

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                final double velocityX = wrapper.motionX / 8000.0D;
                final double velocityZ = wrapper.motionZ / 8000.0D;

                mc.thePlayer.attackedAtYaw = (float) (MathHelper.atan2(velocityX, velocityZ) * 180.0D / Math.PI - (double) mc.thePlayer.rotationYaw);
            }
        }
    };

    @Override
    protected void onDisable() {
        mc.thePlayer.attackedAtYaw = 0;
    }
}