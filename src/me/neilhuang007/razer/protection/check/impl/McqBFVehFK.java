package me.neilhuang007.razer.protection.check.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;

/**
 * @author Strikeless
 * @since 24.04.2022
 */
public class McqBFVehFK extends ProtectionCheck {

    public McqBFVehFK() {
        super(McqBFVadWB.JOIN, false);
    }

    @Override
    public boolean check() throws Throwable {
        // Every time we join a world, check whether the repetitive
        // check handler thread has died, if it has, immediately crash the client.
        // I would do this on a repetitive trigger but... well, you know.
        final Thread repetitiveHandlerThread = Client.INSTANCE.getMcqAFVeaWB().getRepetitiveHandlerThread();

        if (!repetitiveHandlerThread.isAlive() || repetitiveHandlerThread.isInterrupted()) {
            Client.INSTANCE.getMcqAFVeaWB().crash();
            return true;
        }

        return false;
    }
}
