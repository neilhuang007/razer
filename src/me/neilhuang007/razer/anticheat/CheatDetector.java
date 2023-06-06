package me.neilhuang007.razer.anticheat;

import lombok.Getter;
import me.neilhuang007.razer.anticheat.alert.AlertManager;
import me.neilhuang007.razer.anticheat.check.manager.CheckManager;
import me.neilhuang007.razer.anticheat.data.PlayerData;
import me.neilhuang007.razer.anticheat.listener.RegistrationListener;
import net.minecraft.client.Minecraft;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class CheatDetector {

    public final Map<UUID, PlayerData> playerMap = new ConcurrentHashMap<>();

    private final RegistrationListener registrationListener = new RegistrationListener();
    private final AlertManager alertManager = new AlertManager();

    public CheatDetector() {
        CheckManager.setup();
    }

    public void incrementTick() {
        for (PlayerData data : playerMap.values()) {
            if (Minecraft.getMinecraft().theWorld.playerEntities.contains(data.getPlayer())) {
                data.incrementTick();
            } else {
                registrationListener.handleDestroy(data.getPlayer().getUniqueID());
            }
        }
    }
}
