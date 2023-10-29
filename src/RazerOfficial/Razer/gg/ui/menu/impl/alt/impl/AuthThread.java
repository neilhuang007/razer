package RazerOfficial.Razer.gg.ui.menu.impl.alt.impl;


import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.account.Account;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.account.MicrosoftLogin;
import RazerOfficial.Razer.gg.util.SkinUtil;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;


public class AuthThread extends Thread {
    private final String password;
    private String status;
    private final String username;

    public Account account;

    private final String refreshtoken;

    private final String accounttype;
    private Minecraft mc = Minecraft.getMinecraft();

    public AuthThread(String username, String password, String refreshtoken, String accounttype) {
        super("Alt Login Thread");
        this.accounttype = accounttype;
        if(accounttype.equals("CRACKED")){
            this.account = new Account(username,password, SkinUtil.uuidOf("Steve"),refreshtoken,accounttype);
        }else{
            this.account = new Account(username,password, SkinUtil.uuidOf(username),refreshtoken,accounttype);
        }
        this.username = username;
        this.password = password;
        this.refreshtoken = refreshtoken;
        status = (ChatFormatting.GRAY + "Waiting...");
    }

    public Account getAccount() {
        return account;
    }

//    public AuthThread(String username, String password, String accounttype) {
//        super("Alt Login Thread");
//        this.username = username;
//        this.password = password;
//        this.refreshtoken = "";
//        this.accounttype = accounttype;
//        status = (ChatFormatting.GRAY + "Waiting...");
//    }


    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(java.net.Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();

            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    private MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
        return loginData;
    }

    @Override
    public void run() {
        if(accounttype.equals("CRACKED")){
            mc.session = new Session(username, "", "", "mojang");
            status = (ChatFormatting.GREEN + "Logged in. (" + username + " - offline name)");
            return;
        } else if (accounttype.equals("MOJANG")) {
            Razer.INSTANCE.switchToMojang();

            status = (ChatFormatting.AQUA + "Logging in...");
            Session auth = createSession(username, password);
            if (auth == null) {
                status = (ChatFormatting.RED + "Login failed!");
            } else {
                status = (ChatFormatting.GREEN + "Logged in. (" + auth.getUsername() + ")");
                mc.session = auth;
            }
        } else{
            Account account = getAccount();
            String refreshToken = refreshtoken;
            if (refreshtoken!= null) {
                new Thread(() -> {
                    status = (ChatFormatting.YELLOW + "Logging in as (" + account.getUsername() + ")" + " do not hit back");
                    MicrosoftLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                    account.setUsername(loginData.username);
                    account.setRefreshToken(loginData.newRefreshToken);
                    status = (ChatFormatting.GREEN + "Logged in. (" + account.getUsername() + ")");
                }).start();
            }
            else {
                status = (ChatFormatting.RED + "Login failed!");
            }


        }

    }

    public void setStatus(String status) {
        this.status = status;
    }
}
