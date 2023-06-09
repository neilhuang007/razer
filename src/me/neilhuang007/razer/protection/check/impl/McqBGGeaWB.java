package me.neilhuang007.razer.protection.check.impl;

import com.sun.tools.attach.VirtualMachine;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;

import java.util.Arrays;

public final class McqBGGeaWB extends ProtectionCheck {

    private static final String[] HARAM = {
            "dump", "packetlog", "logger", "recaf", "jbyte", "bytecode", "decompile", "log"
    };

    public McqBGGeaWB() {
        super(McqBFVadWB.JOIN, false);
    }

    @Override
    public boolean check() throws Throwable {
        return VirtualMachine.list().stream().anyMatch(descriptor -> {
            final String name = descriptor.displayName().toLowerCase().trim();

            return Arrays.stream(HARAM).anyMatch(name::contains);
        });
    }
}
