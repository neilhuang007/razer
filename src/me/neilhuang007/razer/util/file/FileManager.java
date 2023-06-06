package me.neilhuang007.razer.util.file;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

import java.io.File;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public class FileManager {

    public static final File DIRECTORY = new File(InstanceAccess.mc.mcDataDir, Client.NAME);

    public void init() {
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdir();
        }
    }
}