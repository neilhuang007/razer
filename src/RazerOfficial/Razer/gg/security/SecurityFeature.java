package RazerOfficial.Razer.gg.security;

import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import net.minecraft.network.Packet;

public abstract class SecurityFeature implements InstanceAccess {

    private final String check, description;

    public SecurityFeature(final String check, final String description) {
        this.check = check;
        this.description = description;
    }

    public abstract boolean handle(final Packet<?> packet);
}
