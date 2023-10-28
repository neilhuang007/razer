package RazerOfficial.Razer.gg.ui.menu.impl.alt.account;

import RazerOfficial.Razer.gg.util.SkinUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;


@Getter
@Setter
public class Account {

    private String username;
    private String AccountType;
    private String password;
    private String uuid;
    private String refreshToken;


    public Account(final String usernane, final String password, String uuid, String refreshToken, String accountType) {
        this.username = usernane;
        this.password = password;

        if("CRACKED".equals(accountType)){
            this.uuid = SkinUtil.uuidOf("Steve");
        }
        else {
            this.uuid = SkinUtil.uuidOf(username);
        }
        //this.refreshToken = refreshToken;
        this.AccountType = accountType;
    }

    public Account(final String usernane, final String password) {
        this.username = usernane;
        this.password = password;
        this.uuid = SkinUtil.uuidOf("Steve");
        this.refreshToken = "";
        this.AccountType = "MOJANG";
    }

    public Account(String username, String uuid, String refreshToken) {
        this.username = username;
        this.password = "";
        this.uuid = uuid;
        this.refreshToken = refreshToken;
        this.AccountType = "MICROSOFT";
    }

    public Account(String username) {
        this.username = username;
        this.password = "";
        this.uuid = SkinUtil.uuidOf("Steve");
        this.refreshToken = "";
        this.AccountType = "CRACKED";
    }



    public void setUsername(String username) {
        this.username = username;
        this.uuid = SkinUtil.uuidOf(username);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
