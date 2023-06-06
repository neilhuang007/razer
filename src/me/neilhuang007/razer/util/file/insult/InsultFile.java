package me.neilhuang007.razer.util.file.insult;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.module.impl.other.Insults;
import me.neilhuang007.razer.util.file.File;
import me.neilhuang007.razer.util.file.FileType;
import me.neilhuang007.razer.value.impl.SubMode;

import java.nio.file.Files;

public final class InsultFile extends File {

    public InsultFile(final java.io.File file, final FileType fileType) {
        super(file, fileType);
    }

    @Override
    public boolean read() {
        if (!this.getFile().exists() || !this.getFile().isFile() || !this.getFile().canRead()) return false;

        try {
            final Insults insults = Client.INSTANCE.getModuleManager().get(Insults.class);
            final String name = this.getFile().getName().replace(".txt", "");

            insults.mode.add(new SubMode(name));
            insults.map.put(name, Files.readAllLines(this.getFile().toPath()));

            return true;
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean write() {
        try {
            if (!this.getFile().exists()) this.getFile().createNewFile();

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
