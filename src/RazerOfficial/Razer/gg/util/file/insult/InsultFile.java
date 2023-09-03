package RazerOfficial.Razer.gg.util.file.insult;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.module.impl.other.Insults;
import RazerOfficial.Razer.gg.util.file.File;
import RazerOfficial.Razer.gg.util.file.FileType;
import RazerOfficial.Razer.gg.value.impl.SubMode;

import java.nio.file.Files;

public final class InsultFile extends File {

    public InsultFile(final java.io.File file, final FileType fileType) {
        super(file, fileType);
    }

    @Override
    public boolean read() {
        if (!this.getFile().exists() || !this.getFile().isFile() || !this.getFile().canRead()) return false;

        try {
            final Insults insults = Razer.INSTANCE.getModuleManager().get(Insults.class);
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
