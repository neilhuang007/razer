package net.minecraft.util;

import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Util {
    public static EnumOS getOSType() {
        final String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }

    public static <V> V func_181617_a(final FutureTask<V> p_181617_0_, final Logger p_181617_1_) {
        try {
            p_181617_0_.run();
            return p_181617_0_.get();
        } catch (final ExecutionException executionexception) {
            p_181617_1_.fatal("Error executing task", executionexception);

            if (executionexception.getCause() instanceof OutOfMemoryError) {
                final OutOfMemoryError outofmemoryerror = (OutOfMemoryError) executionexception.getCause();
                throw outofmemoryerror;
            }
        } catch (final InterruptedException interruptedexception) {
            p_181617_1_.fatal("Error executing task", interruptedexception);
        }

        return null;
    }

    public enum EnumOS {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN
    }
}
