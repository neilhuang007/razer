package me.neilhuang007.razer.ui.menu.impl.alt;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.ui.menu.Menu;
import me.neilhuang007.razer.ui.menu.component.button.MenuButton;
import me.neilhuang007.razer.ui.menu.component.button.impl.MenuFeedBackTextButton;

import me.neilhuang007.razer.util.MouseUtil;
import me.neilhuang007.razer.util.account.Account;
import me.neilhuang007.razer.util.account.microsoft.MicrosoftLogin;
import me.neilhuang007.razer.util.animation.Animation;
import me.neilhuang007.razer.util.animation.Easing;
import me.neilhuang007.razer.util.gui.ScrollUtil;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.render.ColorUtil;
import me.neilhuang007.razer.util.render.RenderUtil;
import me.neilhuang007.razer.util.render.ScissorUtil;
import me.neilhuang007.razer.util.shader.RiseShaders;
import me.neilhuang007.razer.util.shader.base.ShaderRenderType;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;
import util.time.StopWatch;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class AltManagerMenu extends Menu {

    private static final double ACCOUNT_WIDTH = 180;
    private static final double ACCOUNT_HEIGHT = 32;
    private static final int ACCOUNT_SPACING = 6;

    private static final int BOX_SPACING = 10;
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 24;
    private static final int BUTTON_SPACING = 6;



    private MenuFeedBackTextButton loginThroughBrowserButton;
    private MenuButton[] menuButtons;

    private Animation animation = new Animation(Easing.EASE_OUT_QUINT, 500);

    private StopWatch click = new StopWatch();
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        // Renders the background
        RiseShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);

        // Update animations
        animation.run(this.height);

        // Handles scrolling


        ScaledResolution scaledResolution = new ScaledResolution(mc);

        NORMAL_BLUR_RUNNABLES.add(() -> RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK));

        // Run blur
        RiseShaders.GAUSSIAN_BLUR_SHADER.update();
        RiseShaders.GAUSSIAN_BLUR_SHADER.run(ShaderRenderType.OVERLAY, mc.timer.renderPartialTicks, InstanceAccess.NORMAL_BLUR_RUNNABLES);

        // Run bloom
        RiseShaders.POST_BLOOM_SHADER.update();
        RiseShaders.POST_BLOOM_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES);

        // Run post shader things
        NORMAL_BLUR_RUNNABLES.clear();
        NORMAL_POST_BLOOM_RUNNABLES.clear();


        // TODO: Don't forget to NOT render the displays out of the screen to save performance
    }


}
