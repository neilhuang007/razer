package me.neilhuang007.razer.protection.check.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;

/**
 * @author Strikeless
 * @since 25.03.2022
 */
public final class McqBFVsdWB extends ProtectionCheck {

    public McqBFVsdWB() {
        super(McqBFVadWB.REPETITIVE, false);

        // Make an attempt at removing the security manager if one is present for some reason.
        System.setSecurityManager(null);
    }

    @Override
    public boolean check() {
        if (System.getSecurityManager() != null) {
            Client.INSTANCE.getMcqAFVeaWB().crash();
            return true;
        }

        return false;
    }
}
