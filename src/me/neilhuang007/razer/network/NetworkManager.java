package me.neilhuang007.razer.network;

import by.radioegor146.nativeobfuscator.Native;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

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
