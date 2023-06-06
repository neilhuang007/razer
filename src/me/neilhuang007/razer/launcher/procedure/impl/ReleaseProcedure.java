package me.neilhuang007.razer.launcher.procedure.impl;

import lombok.SneakyThrows;
import me.neilhuang007.razer.launcher.auth.Authenticator;
import me.neilhuang007.razer.launcher.procedure.LaunchProcedure;

public final class ReleaseProcedure implements LaunchProcedure {

    @Override
    @SneakyThrows
    public void launch() {
        if (!Authenticator.isValid()) return;

        // TODO: 30.07.2022 finish
    }
}
