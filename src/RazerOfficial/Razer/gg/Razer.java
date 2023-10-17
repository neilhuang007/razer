package RazerOfficial.Razer.gg;

import RazerOfficial.Razer.gg.api.Hidden;
import RazerOfficial.Razer.gg.bots.BotManager;
import RazerOfficial.Razer.gg.command.Command;
import RazerOfficial.Razer.gg.command.CommandManager;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.component.ComponentManager;
import RazerOfficial.Razer.gg.creative.RiseTab;
import RazerOfficial.Razer.gg.event.bus.impl.EventBus;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.manager.ModuleManager;
import RazerOfficial.Razer.gg.network.NetworkManager;
import RazerOfficial.Razer.gg.packetlog.Check;
import RazerOfficial.Razer.gg.packetlog.api.manager.PacketLogManager;
import RazerOfficial.Razer.gg.protection.manager.TargetManager;
import RazerOfficial.Razer.gg.script.ScriptManager;
import RazerOfficial.Razer.gg.security.SecurityFeatureManager;
import RazerOfficial.Razer.gg.ui.click.clover.CloverClickGUI;
import RazerOfficial.Razer.gg.ui.click.dropdown.DropdownClickGUI;
import RazerOfficial.Razer.gg.ui.click.standard.RiseClickGUI;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.GuiAccountManager;
import RazerOfficial.Razer.gg.ui.menu.impl.alt.account.AccountManager;
import RazerOfficial.Razer.gg.ui.theme.ThemeManager;
import RazerOfficial.Razer.gg.util.ReflectionUtil;
import RazerOfficial.Razer.gg.util.file.FileManager;
import RazerOfficial.Razer.gg.util.file.FileType;

import RazerOfficial.Razer.gg.util.file.config.ConfigFile;
import RazerOfficial.Razer.gg.util.file.config.ConfigManager;
import RazerOfficial.Razer.gg.util.file.data.DataManager;
import RazerOfficial.Razer.gg.util.localization.Locale;
import RazerOfficial.Razer.gg.util.math.MathConst;
import RazerOfficial.Razer.gg.util.thealtening.AltService;
import RazerOfficial.Razer.gg.util.value.ConstantManager;
import by.radioegor146.nativeobfuscator.Native;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main class where the client is loaded up.
 * Anything related to the client will start from here and managers etc instances will be stored in this class.
 *
 * @author Tecnio
 * @since 29/08/2021
 */
@Getter
@Native
public enum Razer {

    /**
     * Simple enum instance for our client as enum instances
     * are immutable and are very easy to create and use.
     */
    INSTANCE;

    public static String NAME = "Razer";
    public static String VERSION = "1.0";
    public static String VERSION_FULL = "1.0"; // Used to give more detailed build info on beta builds
    public static String VERSION_DATE = "June 5, 2023";

    public static boolean DEVELOPMENT_SWITCH = true;
    public static boolean BETA_SWITCH = true;
    public static boolean FIRST_LAUNCH = true;
    public static Type CLIENT_TYPE = Type.RISE;




    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Setter
    private Locale locale = Locale.EN_US; // The language of the client

    private EventBus eventBus;
    private ModuleManager moduleManager;
    private ComponentManager componentManager;
    private CommandManager commandManager;
    private SecurityFeatureManager securityManager;
    private BotManager botManager;
    private ThemeManager themeManager;
    @Setter
    private NetworkManager networkManager;
    @Setter
    private ScriptManager scriptManager;
    private DataManager dataManager;

    private FileManager fileManager;

    private ConfigManager configManager;
    private AltService altService;
    private AccountManager AccountManager;

    private TargetManager targetManager;
    private ConstantManager constantManager;
    private PacketLogManager packetLogManager;

    private ConfigFile configFile;

    private CloverClickGUI cloverClickGUI;
    private RiseClickGUI standardClickGUI;
    private DropdownClickGUI dropdownClickGUI;
    private GuiAccountManager altManagerMenu;

    private RiseTab creativeTab;

    @Setter
    private boolean validated;

    public void switchToMojang() {
        try {
            altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to modank altservice");
        }
    }
    public void switchToTheAltening() {
        try {
            altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to altening altservice");
        }
    }

    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */
    public void initRise() {


        // Init
        Minecraft mc = Minecraft.getMinecraft();
        MathConst.calculate();

        // Compatibility
        mc.gameSettings.guiScale = 2;
        mc.gameSettings.ofFastRender = false;
        mc.gameSettings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        // Performance
        mc.gameSettings.ofSmartAnimations = true;
        mc.gameSettings.ofSmoothFps = false;
        mc.gameSettings.ofFastMath = false;

        File ALT_DIRECTORY = new File(FileManager.DIRECTORY, "alts");

        this.moduleManager = new ModuleManager();
        this.componentManager = new ComponentManager();
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.configManager = new ConfigManager();
        this.AccountManager = new AccountManager(ALT_DIRECTORY);
        this.dataManager = new DataManager();
        this.securityManager = new SecurityFeatureManager();
        this.botManager = new BotManager();
        this.themeManager = new ThemeManager();
        this.scriptManager = new ScriptManager();
        this.targetManager = new TargetManager();
        this.altService = new AltService();

        this.constantManager = new ConstantManager();
        this.eventBus = new EventBus();
        this.packetLogManager = new PacketLogManager();

        // Register
        String[] paths = {
                "RazerOfficial.Razer.gg"
        };

        for (String path : paths) {
            if (!ReflectionUtil.dirExist(path)) {
                continue;
            }

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> clazz : classes) {
                try {
                    if (clazz.isAnnotationPresent(Hidden.class)) continue;

                    if (Component.class.isAssignableFrom(clazz) && clazz != Component.class) {
                        this.componentManager.add((Component) clazz.getConstructor().newInstance());
                    } else if (Module.class.isAssignableFrom(clazz) && clazz != Module.class) {
                        this.moduleManager.add((Module) clazz.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(clazz) && clazz != Command.class) {
                        this.commandManager.add((Command) clazz.getConstructor().newInstance());
                    } else if (Check.class.isAssignableFrom(clazz) && clazz != Check.class) {
                        this.packetLogManager.add((Check) clazz.getConstructor().newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            }

            break;
        }

        // Init Managers
        this.targetManager.init();
        this.dataManager.init();
        this.moduleManager.init();
        this.securityManager.init();
        this.botManager.init();
        this.componentManager.init();
        this.commandManager.init();
        this.fileManager.init();
        this.configManager.init();
        this.scriptManager.init();
        this.packetLogManager.init();

        final File file = new File(ConfigManager.CONFIG_DIRECTORY, "latest.json");
        this.configFile = new ConfigFile(file, FileType.CONFIG);
        this.configFile.allowKeyCodeLoading();
        this.configFile.read();



        this.standardClickGUI = new RiseClickGUI();
        this.dropdownClickGUI = new DropdownClickGUI();
        this.altManagerMenu = new GuiAccountManager();

        this.creativeTab = new RiseTab();

        Display.setTitle(NAME + " " + VERSION_FULL);
    }

    /**
     * The terminate method is called when the Minecraft client is shutting
     * down, so we can cleanup our stuff and ready ourselves for the client quitting.
     */
    public void terminate() {
        this.configFile.write();
    }


}

