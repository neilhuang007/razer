package RazerOfficial.Razer.gg.network;

import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import by.radioegor146.nativeobfuscator.Native;
import lombok.Getter;
import lombok.Setter;

@Getter
@Native
public final class NetworkManager implements InstanceAccess {
    @Setter
    public String username;
    public String message;

    public void init(String username) {
        this.username = username;
    }

    public static boolean a() {
        return true;
    }
}
