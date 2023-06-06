package me.neilhuang007.razer.protection.check.impl;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import me.neilhuang007.razer.protection.check.api.McqBFVadWB;
import me.neilhuang007.razer.util.OSUtil;

import java.io.File;
import java.nio.file.Files;
import java.util.Locale;

public final class MhdBFVeaWB extends ProtectionCheck {

    private final File hostsFile;

    public MhdBFVeaWB() {
        super(McqBFVadWB.INITIALIZE, false);

        this.hostsFile = new File(OSUtil.getPlatform() == OSUtil.OS.WINDOWS
                ? System.getenv("WinDir") + "\\System32\\drivers\\etc\\hosts"
                : "/etc/hosts");
    }

    @Override
    public boolean check() throws Throwable {
        new Thread(() -> {
            try {
                for (; ; ) {
                    if (!this.hostsFile.exists() || !this.hostsFile.canRead() || !this.hostsFile.isFile()) {
                        Client.INSTANCE.getMcqAFVeaWB().crash();
                    }

                    for (final String line : Files.readAllLines(this.hostsFile.toPath())) {
                        final String format = line.toLowerCase(Locale.ENGLISH).trim();

                        if (format.contains("intent.store") || format.contains("riseclient.com") || format.contains("vantage")) {
                            Client.INSTANCE.getMcqAFVeaWB().crash();
                        }
                    }

                    Thread.sleep(5000L);
                }
            } catch (final Throwable t) {
                Client.INSTANCE.getMcqAFVeaWB().crash();
            }
        }).start();

        return false;
    }
}
