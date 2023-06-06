package me.neilhuang007.razer.launcher;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.launcher.procedure.LaunchProcedure;
import me.neilhuang007.razer.launcher.procedure.ProcedureFactory;

public final class Launcher {

    public static final String BASE = "http://localhost:3000";

    public void launch() {
        final LaunchProcedure procedure = new ProcedureFactory()
                .setDeveloper(Client.DEVELOPMENT_SWITCH)
                .build();

        procedure.launch();
    }
}
