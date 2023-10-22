package RazerOfficial.Razer.gg.ui.menu.impl.alt.impl;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.GuiAccountManager;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.account.Account;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.account.MicrosoftLogin;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.net.Proxy;

public class GuiMicrosoftLogin extends GuiScreen {
    private final GuiAccountManager manager;

//    private class AddAltThread extends Thread {
//        private final String password;
//        private final String username;
//
//        public AddAltThread(String username, String password) {
//            this.username = username;
//            this.password = password;
//            status = (EnumChatFormatting.GRAY + "Idle...");
//        }
//
//
//
//        private final void checkAndAddAlt(String username, String password) {
//            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
//            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
//                    .createUserAuthentication(com.mojang.authlib.Agent.MINECRAFT);
//            auth.setUsername(username);
//            auth.setPassword(password);
//            try {
//                auth.logIn();
//                Razer.INSTANCE.getAccountManager().getAccounts()
//                        .add(new Account(username, password, auth.getSelectedProfile().getName()));
//                Razer.INSTANCE.getAccountManager().getAltSaving().saveFile();
//                status = ("Alt added. (" + username + ")");
//            } catch (AuthenticationException e) {
//                status = (EnumChatFormatting.RED + "Alt failed!");
//                e.printStackTrace();
//
//            }
//        }

//        @Override
//        public void run() {
//            if (password.equals("") && combined.getText().isEmpty()) {
//                Razer.INSTANCE.getAccountManager().getAccounts().add(new Account(username, ""));
//                status = (EnumChatFormatting.GREEN + "Alt added. (" + username + " - offline name)");
//                return;
//            }
//            status = (EnumChatFormatting.AQUA + "Trying alt...");
//            checkAndAddAlt(username, password);
//        }
//    }

    private String status = EnumChatFormatting.GRAY + "Idle...";

    public GuiMicrosoftLogin(GuiAccountManager manager) {
        this.manager = manager;
    }


    private MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
        return loginData;
    }
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                MicrosoftLogin.getRefreshToken(refreshToken -> {
                    if (refreshToken != null) {
                        new Thread(() -> {
                            MicrosoftLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                            Account account = new Account(loginData.username, "************");
                            account.setUsername(loginData.username);
                            account.setRefreshToken(loginData.newRefreshToken); // TODO: THIS IS IMPORTANT
                            Razer.INSTANCE.getAccountManager().getAccounts().add(account);
                        }).start();
                    }
                });
                break;
            case 1:
                mc.displayGuiScreen(manager);
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        int var3 = height / 4 + 24;
        drawDefaultBackground();
        Minecraft.getMinecraft().fontRendererObj.drawCenteredString( "Alt Login", width / 2, 20, -1);
        Minecraft.getMinecraft().fontRendererObj.drawCenteredString(status, width / 2, 29, -1);
        super.drawScreen(i, j, f);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        int var3 = height / 4 + 24;
        buttonList.add(new GuiButton(0, width / 2 - 96, var3 + 72 + 12, "Login"));
        buttonList.add(new GuiButton(1, width / 2 - 96, var3 + 72 + 12 + 24, "Back"));
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par1 == '\r') {
            actionPerformed(buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
