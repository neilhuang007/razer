package RazerOfficial.Razer.gg.ui.menu.impl.alt.account;

import RazerOfficial.Razer.gg.util.file.FileManager;
import RazerOfficial.Razer.gg.util.file.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AccountManager extends ArrayList<AltSaving>{
    public static final File ALT_DIRECTORY = new File(FileManager.DIRECTORY, "alts");
    private final List<Account> accounts = new ArrayList<>();

    public void init() {
        if (!ALT_DIRECTORY.exists()) {
            ALT_DIRECTORY.mkdir();
        }
    }

    public AltSaving get(final String alts) {
        final File file = new File(AccountManager.ALT_DIRECTORY, alts + ".json");
        return new AltSaving(file, FileType.ACCOUNT);
    }

    public void set(final String alts) {
        final File file = new File(ALT_DIRECTORY, alts + ".json");
        AltSaving AltSaving = get(alts);

        if (AltSaving == null) {
            AltSaving = new AltSaving(file, FileType.ACCOUNT);
            add(AltSaving);

            System.out.println("Creating new alts...");
        } else {
            System.out.println("Overwriting existing alts...");
        }

        AltSaving.write();

        System.out.println("Config saved to files.");
    }

    public boolean update() {
        accounts.clear();

        final File[] files = ALT_DIRECTORY.listFiles();

        if (files == null)
            return false;

        for (final File file : files) {
            if (file.getName().endsWith(".json")) {
                add(new AltSaving(file, FileType.ACCOUNT));
            }
        }

        return true;
    }

    public boolean delete(final String config) {
        final AltSaving AltSaving = get(config);

        if (AltSaving == null)
            return false;

        remove(AltSaving);
        return AltSaving.getFile().delete();
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
