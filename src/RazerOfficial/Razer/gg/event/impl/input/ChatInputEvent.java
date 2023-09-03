package RazerOfficial.Razer.gg.event.impl.input;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
@AllArgsConstructor
public final class ChatInputEvent extends CancellableEvent {
    private String message;
}