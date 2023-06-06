package me.neilhuang007.razer.newevent.impl.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.CancellableEvent;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
@AllArgsConstructor
public final class GuiClickEvent extends CancellableEvent {
    private final int mouseX, mouseY, mouseButton;
}