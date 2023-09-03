package RazerOfficial.Razer.gg.ui.menu.impl.alt;

import RazerOfficial.Razer.gg.ui.menu.Menu;
import RazerOfficial.Razer.gg.ui.menu.component.button.MenuButton;
import RazerOfficial.Razer.gg.ui.menu.component.button.impl.MenuFeedBackTextButton;
import RazerOfficial.Razer.gg.util.animation.Animation;
import RazerOfficial.Razer.gg.util.animation.Easing;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.shader.RiseShaders;
import RazerOfficial.Razer.gg.util.shader.base.ShaderRenderType;
import net.minecraft.client.gui.ScaledResolution;
import util.time.StopWatch;

import java.awt.*;

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
