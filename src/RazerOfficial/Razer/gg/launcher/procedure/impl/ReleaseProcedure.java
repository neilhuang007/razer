package RazerOfficial.Razer.gg.launcher.procedure.impl;

import RazerOfficial.Razer.gg.launcher.auth.Authenticator;
import RazerOfficial.Razer.gg.launcher.procedure.LaunchProcedure;
import lombok.SneakyThrows;

public final class ReleaseProcedure implements LaunchProcedure {

    @Override
    @SneakyThrows
    public void launch() {
        if (!Authenticator.isValid()) return;

        // TODO: 30.07.2022 finish
    }
}
