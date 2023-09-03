package RazerOfficial.Razer.gg.launcher.procedure;

import RazerOfficial.Razer.gg.launcher.procedure.impl.DevProcedure;
import RazerOfficial.Razer.gg.launcher.procedure.impl.ReleaseProcedure;
import util.interfaces.Factory;

public final class ProcedureFactory implements Factory<LaunchProcedure> {

    private boolean dev;

    public ProcedureFactory setDeveloper(final boolean value) {
        this.dev = value;

        return this;
    }

    @Override
    public LaunchProcedure build() {
        return this.dev ? new DevProcedure() : new ReleaseProcedure();
    }
}
