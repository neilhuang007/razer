package me.neilhuang007.razer.security;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.module.impl.other.SecurityFeatures;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
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
        Client.INSTANCE.getEventBus().register(this);

        this.features = Client.INSTANCE.getModuleManager().get(SecurityFeatures.class);

        if (this.features == null) return;

        final Reflections reflections = new Reflections("com.alan.razer.security.impl");

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
