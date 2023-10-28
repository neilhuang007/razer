package RazerOfficial.Razer.gg.ui.menu.impl.alt.account;


import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.util.file.FileType;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;



public class AltSaving extends RazerOfficial.Razer.gg.util.file.File {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

    public AltSaving(final File file, final FileType fileType) {
        super(file, fileType);
    }

    @Override
    public boolean read() {
        if (!this.getFile().exists()) {
            return false;
        }

        Razer.INSTANCE.getAccountManager().getAccounts().clear();

        try {
            // reads file to a json object
            final FileReader fileReader = new FileReader(this.getFile());
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final JsonArray jsonArray = GSON.fromJson(bufferedReader, JsonArray.class);

            // closes both readers
            bufferedReader.close();
            fileReader.close();

            // checks if there was data read
            if (jsonArray == null) {
                return false;
            }

            for (Map.Entry<String, JsonElement> jsonElement : jsonObject.entrySet()) {
                if (jsonElement.getKey().equals("Metadata")) {
                    continue;
                }

                // TODO: Might wanna add "has" checks for each field so it doesn't shit itself while loading
                JsonObject accountJSONElement = jsonElement.getValue().getAsJsonObject();
                String username = accountJSONElement.get("username").getAsString();
                String password = accountJSONElement.get("password").getAsString();
                String uuid = accountJSONElement.get("uuid").getAsString();
                String refreshToken = accountJSONElement.get("refreshtoken").getAsString();
                String accounttype = accountJSONElement.get("accounttype").getAsString();
                Account account = new Account(password, jsonElement.getKey(), uuid, refreshToken);
                Razer.INSTANCE.getAccountManager().getAccounts().add(account);
            }

        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @Override
    public boolean write() {
        try {
            // creates the file
            if (!this.getFile().exists()) {
                this.getFile().createNewFile();
            }

            // creates a new json object where all data is stored in
            final JsonObject jsonObject = new JsonObject();

            // Add some extra information to the config
            final JsonObject metadataJsonObject = new JsonObject();
            metadataJsonObject.addProperty("version", Razer.VERSION);
            metadataJsonObject.addProperty("creationDate", DATE_FORMATTER.format(new Date()));
            jsonObject.add("Metadata", metadataJsonObject);

            for (Account account : Razer.INSTANCE.getAccountManager().getAccounts()) {
                if (account.getUsername() == null) {
                    continue;
                }

                final JsonObject moduleJsonObject = new JsonObject();
                moduleJsonObject.addProperty("username", account.getUsername());
                moduleJsonObject.addProperty("password", account.getPassword());
                moduleJsonObject.addProperty("uuid", account.getUuid());
                moduleJsonObject.addProperty("refreshtoken", account.getRefreshToken());
                moduleJsonObject.addProperty("accounttype", account.getAccountType());
                jsonObject.add(account.getUsername(), moduleJsonObject);
            }

            // writes json object data to a file
            final FileWriter fileWriter = new FileWriter(getFile());
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            GSON.toJson(jsonObject, bufferedWriter);

            // closes the writer
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }
}
