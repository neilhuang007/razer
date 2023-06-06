package me.neilhuang007.razer.util.dragging;

import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.vector.Vector2d;
import net.minecraft.client.gui.ScaledResolution;

public class Mouse {
    public static Vector2d getMouse() {
        final ScaledResolution scaledResolution = InstanceAccess.mc.scaledResolution;
        final int mouseX = org.lwjgl.input.Mouse.getX() * scaledResolution.getScaledWidth() / InstanceAccess.mc.displayWidth;
        final int mouseY = scaledResolution.getScaledHeight() - org.lwjgl.input.Mouse.getY() * scaledResolution.getScaledHeight() / InstanceAccess.mc.displayHeight - 1;

        return new Vector2d(mouseX, mouseY);
    }
}
