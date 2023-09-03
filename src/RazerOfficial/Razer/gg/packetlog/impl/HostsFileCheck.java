package RazerOfficial.Razer.gg.packetlog.impl;

import RazerOfficial.Razer.gg.component.impl.player.LastConnectionComponent;
import RazerOfficial.Razer.gg.packetlog.Check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HostsFileCheck extends Check {
    @Override
    public boolean run() {
        if (LastConnectionComponent.ip != null && System.getProperty("os.name").toLowerCase().contains("win")) {
            final String separator = File.separator;
            final File file = new File(System.getenv("windir") + separator + "System32" + separator + "drivers" + separator + "etc" + separator + "hosts");

            if (file.exists() && !file.isDirectory()) {
                try {
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.toLowerCase().contains(LastConnectionComponent.ip.toLowerCase())) {
                            return true;
                        }
                    }
                } catch (final IOException ignored) {
                }
            }
        }

        return false;
    }
}
