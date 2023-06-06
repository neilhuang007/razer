package me.neilhuang007.razer.protection.check.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;

import java.util.List;

/**
 * @author Strikeless
 * @since 25.03.2022
 */
public final class McqBFVeaWB extends ProtectionCheck {

    public McqBFVeaWB() {
        super(McqBFVadWB.INITIALIZE, true);
    }

    @Override
    public boolean check() {
        final List<String> arguments = Client.INSTANCE.getMcqAFVeaWB().getJvmArguments();

        final boolean malicious = arguments.stream().anyMatch(s -> s.contains("javaagent")
                || s.contains("agentlib") || s.contains("Xdebug")
                || s.contains("Xrunjdwp:") || s.contains("noverify")
        );
        final boolean required = arguments.contains("-XX:+DisableAttachMechanism");

        return malicious || !required;
    }
}
