package me.neilhuang007.razer.newevent.impl.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.Event;
import me.neilhuang007.razer.util.vector.Vector2f;

@Getter
@Setter
@AllArgsConstructor
public final class LookEvent implements Event {
    private Vector2f rotation;
}
