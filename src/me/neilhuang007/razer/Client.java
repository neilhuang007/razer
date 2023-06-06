package me.neilhuang007.razer;

import by.radioegor146.nativeobfuscator.Native;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.anticheat.CheatDetector;
import me.neilhuang007.razer.api.Hidden;
import me.neilhuang007.razer.bots.BotManager;
import me.neilhuang007.razer.command.Command;
import me.neilhuang007.razer.command.CommandManager;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.component.ComponentManager;
import me.neilhuang007.razer.creative.RiseTab;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.manager.ModuleManager;
import me.neilhuang007.razer.network.NetworkManager;
import me.neilhuang007.razer.newevent.bus.impl.EventBus;
import me.neilhuang007.razer.packetlog.Check;
import me.neilhuang007.razer.packetlog.api.manager.PacketLogManager;
import me.neilhuang007.razer.protection.check.api.McqBFVbnWB;
import me.neilhuang007.razer.protection.manager.TargetManager;
import me.neilhuang007.razer.script.ScriptManager;
import me.neilhuang007.razer.security.SecurityFeatureManager;
import me.neilhuang007.razer.ui.click.clover.CloverClickGUI;
import me.neilhuang007.razer.ui.click.dropdown.DropdownClickGUI;
import me.neilhuang007.razer.ui.click.standard.RiseClickGUI;
import me.neilhuang007.razer.ui.menu.impl.alt.AltManagerMenu;
import me.neilhuang007.razer.ui.theme.ThemeManager;
import me.neilhuang007.razer.util.ReflectionUtil;
import me.neilhuang007.razer.util.file.FileManager;
import me.neilhuang007.razer.util.file.FileType;
import me.neilhuang007.razer.util.file.alt.AltManager;
import me.neilhuang007.razer.util.file.config.ConfigFile;
import me.neilhuang007.razer.util.file.config.ConfigManager;
import me.neilhuang007.razer.util.file.data.DataManager;
import me.neilhuang007.razer.util.file.insult.InsultFile;
import me.neilhuang007.razer.util.file.insult.InsultManager;
import me.neilhuang007.razer.util.localization.Locale;
import me.neilhuang007.razer.util.math.MathConst;
import me.neilhuang007.razer.util.value.ConstantManager;
import net.minecraft.client.Minecraft;
import net.minecraft.viamcp.ViaMCP;
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
public enum Client {

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
    private McqBFVbnWB McqAFVeaWB;
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
    private CheatDetector cheatDetector;

    private FileManager fileManager;

    private ConfigManager configManager;
    private AltManager altManager;
    private InsultManager insultManager;
    private TargetManager targetManager;
    private ConstantManager constantManager;
    private PacketLogManager packetLogManager;

    private ConfigFile configFile;

    private CloverClickGUI cloverClickGUI;
    private RiseClickGUI standardClickGUI;
    private DropdownClickGUI dropdownClickGUI;
    private AltManagerMenu altManagerMenu;

    private RiseTab creativeTab;

    @Setter
    private boolean validated;

    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */
    public void initRise() {
        // Crack Protection
//        if (!this.validated && !DEVELOPMENT_SWITCH) {
//            return;
//        }

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

        this.McqAFVeaWB = new McqBFVbnWB();
        this.moduleManager = new ModuleManager();
        this.componentManager = new ComponentManager();
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.configManager = new ConfigManager();
        this.altManager = new AltManager();
        this.insultManager = new InsultManager();
        this.dataManager = new DataManager();
        this.securityManager = new SecurityFeatureManager();
        this.botManager = new BotManager();
        this.themeManager = new ThemeManager();
//        this.networkManager = new NetworkManager();
        this.scriptManager = new ScriptManager();
        this.targetManager = new TargetManager();
        this.cheatDetector = new CheatDetector();
        this.constantManager = new ConstantManager();
        this.eventBus = new EventBus();
        this.packetLogManager = new PacketLogManager();

        // Register
        String[] paths = {
                "me.neilhuang007.razer",
                "hackclient."
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
        //this.McqAFVeaWB.init();
        this.moduleManager.init();
        this.securityManager.init();
        this.botManager.init();
        this.componentManager.init();
        this.commandManager.init();
        this.fileManager.init();
        this.configManager.init();
        this.altManager.init();
        this.insultManager.init();
        this.scriptManager.init();
        this.packetLogManager.init();

        final File file = new File(ConfigManager.CONFIG_DIRECTORY, "latest.json");
        this.configFile = new ConfigFile(file, FileType.CONFIG);
        this.configFile.allowKeyCodeLoading();
        this.configFile.read();

        this.insultManager.update();
        this.insultManager.forEach(InsultFile::read);

        this.standardClickGUI = new RiseClickGUI();
        this.dropdownClickGUI = new DropdownClickGUI();
        this.altManagerMenu = new AltManagerMenu();

        this.creativeTab = new RiseTab();

        ViaMCP.staticInit();

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

