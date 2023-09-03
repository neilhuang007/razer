package RazerOfficial.Razer.gg.util.file;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;

import java.io.File;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public class FileManager {

    public static final File DIRECTORY = new File(InstanceAccess.mc.mcDataDir, Razer.NAME);

    public void init() {
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdir();
        }
    }
}