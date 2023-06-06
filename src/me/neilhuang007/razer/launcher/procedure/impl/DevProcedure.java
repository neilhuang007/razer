package me.neilhuang007.razer.launcher.procedure.impl;

import lombok.SneakyThrows;
import me.neilhuang007.razer.launcher.procedure.LaunchProcedure;

public final class DevProcedure implements LaunchProcedure {

    @Override
    @SneakyThrows
    public void launch() {
        // comment out before release
        main: {
            final Object object = Class.forName("me.neilhuang007.razer.Client").getEnumConstants()[0];
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
