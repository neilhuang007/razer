package me.neilhuang007.razer.protection.check.api;

import lombok.Getter;
import me.neilhuang007.razer.protection.check.ProtectionCheck;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Strikeless
 * @since 25.03.2022
 */
@Getter
@SuppressWarnings("all") // we doing goofy shit
public final class McqBFVbnWB {

    private List<String> jvmArguments;

    private ProtectionCheck[] checks;

    private Thread repetitiveHandlerThread;

    private boolean initialized;

    public void init() {
    }

    public void hang() {
        while (true) ;
    }

    public void crash() {
        for (; ; ) {
            try {
                final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);

                final Unsafe unsafe = (Unsafe) unsafeField.get(null);

                for (long address = 0; true; ++address) {
                    unsafe.setMemory(address, Long.MAX_VALUE, Byte.MIN_VALUE);
                }
            } catch (final Throwable t) {
                crash();
            }

            hang();
        }
    }
}
