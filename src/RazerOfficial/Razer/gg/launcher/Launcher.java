package RazerOfficial.Razer.gg.launcher;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.launcher.procedure.LaunchProcedure;
import RazerOfficial.Razer.gg.launcher.procedure.ProcedureFactory;

public final class Launcher {

    public static final String BASE = "http://localhost:3000";

    public void launch() {
        final LaunchProcedure procedure = new ProcedureFactory()
                .setDeveloper(Razer.DEVELOPMENT_SWITCH)
                .build();

        procedure.launch();
    }
}
