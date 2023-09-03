package RazerOfficial.Razer.gg.ui.menu.impl.intro;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.ui.menu.impl.main.LoginMenu;
import RazerOfficial.Razer.gg.util.animation.Animation;
import RazerOfficial.Razer.gg.util.animation.Easing;
import RazerOfficial.Razer.gg.util.font.FontManager;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import util.time.StopWatch;

import java.awt.*;

public class PrereleaseDisclaimer extends GuiScreen {
    private final Animation fadeAnimation = new Animation(Easing.EASE_IN_OUT_CUBIC, 1000);
    private final StopWatch stopwatch = new StopWatch();

    @Override
    public void initGui() {
        fadeAnimation.reset();
        stopwatch.reset();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.rectangle(0, 0, mc.displayWidth, mc.displayHeight, Color.BLACK);
        fadeAnimation.run(stopwatch.getElapsedTime() > 4000 ? 0 : 255);

        ScaledResolution sr = new ScaledResolution(mc);
        FontManager.getProductSansRegular(24).drawCenteredString("Note: This is private prerelease software", sr.getScaledWidth() / 2D,
                sr.getScaledHeight() / 2D - 70, ColorUtil.withAlpha(Color.WHITE, (int) fadeAnimation.getValue()).getRGB());
        FontManager.getProductSansRegular(24).drawCenteredString("Features, interfaces and sequences are not final and can be expected to change at any time", sr.getScaledWidth() / 2D,
                sr.getScaledHeight() / 2D - 50, ColorUtil.withAlpha(Color.WHITE, (int) fadeAnimation.getValue()).getRGB());

        FontManager.getProductSansRegular(16).drawCenteredString("© Razer Razer 2023. All Rights Reserved", sr.getScaledWidth() / 2D,
                sr.getScaledHeight() / 2D + 70, ColorUtil.withAlpha(Color.WHITE, (int) fadeAnimation.getValue() / 2).getRGB());

        //ColorUtil.withAlpha(Color.WHITE, (int) fadeAnimation.getValue()).getRGB()

        if (stopwatch.finished(5000) || Razer.DEVELOPMENT_SWITCH) {
            mc.displayGuiScreen(new LoginMenu());
        }
    }
}
