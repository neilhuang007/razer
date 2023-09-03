package RazerOfficial.Razer.gg.security;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.other.SecurityFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import org.reflections.Reflections;

import java.util.ArrayList;

public final class SecurityFeatureManager extends ArrayList<SecurityFeature> {

    private SecurityFeatures features;

    public SecurityFeatureManager() {
        super();
    }

    public void init() {
        Razer.INSTANCE.getEventBus().register(this);

        this.features = Razer.INSTANCE.getModuleManager().get(SecurityFeatures.class);

        if (this.features == null) return;

        final Reflections reflections = new Reflections("com.alan.gg.security.impl");

        reflections.getSubTypesOf(SecurityFeature.class).forEach(clazz -> {
            try {
                this.add(clazz.getConstructor().newInstance());
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean isInsecure(final Packet<?> packet) {
        // Notification
        return this.features != null && this.features.isEnabled()
                && !Minecraft.getMinecraft().isSingleplayer()
                && this.stream().anyMatch(feature -> feature.handle(packet));
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        event.setCancelled(isInsecure(event.getPacket()));
    };
}
