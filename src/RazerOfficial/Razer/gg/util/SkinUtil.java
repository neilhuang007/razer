package RazerOfficial.Razer.gg.util;

import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SkinUtil implements InstanceAccess {

    private static final Map<String, ResourceLocation> SKIN_CACHE = new HashMap<>();
    private static final String NAME_TO_UUID = "https://api.mojang.com/users/profiles/minecraft/";

    public static ResourceLocation getResourceLocation(SkinType skinType, String uuid, int size) {
        if (SKIN_CACHE.containsKey(uuid)) return SKIN_CACHE.get(uuid);

        String imageUrl = "http://crafatar.com/avatars/" + uuid;
        ResourceLocation resourceLocation = new ResourceLocation("skins/" + uuid + "?overlay=true");
        ThreadDownloadImageData headTexture = new ThreadDownloadImageData(null, imageUrl, null, null);
        mc.getTextureManager().loadTexture(resourceLocation, headTexture);
        SKIN_CACHE.put(uuid, resourceLocation);
        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, uuid);
        //System.out.println(uuid);
        return resourceLocation;
    }

    public static String uuidOf(String name) {
        String data = scrape(NAME_TO_UUID + name);
        // sometimes this gets fucked up because of internet issues
        if(data == null){
            // just return the steve one
            return "8667ba71-b85a-4004-af54-457a9734eed7";
        }
        else{
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();

            //System.out.println(data);
            // this should fix internet issue that crashes the server
            if(jsonObject.has("id")){
                if(jsonObject.get("id").equals(null)) return "8667ba71-b85a-4004-af54-457a9734eed7";
                if (jsonObject == null || !jsonObject.has("id")) return "8667ba71-b85a-4004-af54-457a9734eed7";
                return jsonObject.get("id").getAsString();
            }
            else{
                return "8667ba71-b85a-4004-af54-457a9734eed7";
            }
        }




    }

    private static String scrape(String url) {
        String content = "";
        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Chrome Version 88.0.4324.150");
            connection.connect();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content += line + System.lineSeparator();
            }
            bufferedReader.close();
        } catch (IOException ignored) {
        }
        return content;
    }

    public static String name(String uuid) {
        return null;
    }

    public enum SkinType {
        AVATAR, HELM, BUST, ARMOR_BUST, BODY, ARMOR_BODY, CUBE, SKIN
    }
}
