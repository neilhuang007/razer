package me.neilhuang007.razer.anticheat.check.impl.combat;

import me.neilhuang007.razer.anticheat.check.Check;
import me.neilhuang007.razer.anticheat.check.api.CheckInfo;
import me.neilhuang007.razer.anticheat.data.PlayerData;
import net.minecraft.network.Packet;

// Code for this check is in the flight prediction check
@CheckInfo(name = "Velocity", type = "Cancel", description = "Detects velocities")
public final class VelocityCancel extends Check {

    public VelocityCancel(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet<?> packet) {
    }
}