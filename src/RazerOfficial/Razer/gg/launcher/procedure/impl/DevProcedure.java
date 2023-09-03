package RazerOfficial.Razer.gg.launcher.procedure.impl;

import RazerOfficial.Razer.gg.launcher.procedure.LaunchProcedure;
import lombok.SneakyThrows;

public final class DevProcedure implements LaunchProcedure {

    @Override
    @SneakyThrows
    public void launch() {
        // comment out before release
        main: {
            final Object object = Class.forName("RazerOfficial.Razer.gg.Razer").getEnumConstants()[0];
            if (object == null) break main;

            object.getClass()
                    .getMethod("init")
                    .invoke(object);
        }

        Class.forName("net.minecraft.viamcp.ViaMCP")
                .getMethod("staticInit")
                .invoke(null);
    }
}
