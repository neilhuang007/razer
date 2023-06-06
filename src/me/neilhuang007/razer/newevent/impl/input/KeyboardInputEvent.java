package me.neilhuang007.razer.newevent.impl.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
@AllArgsConstructor
public final class KeyboardInputEvent implements Event {
    private final int keyCode;
    private final GuiScreen guiScreen;
}
