package me.neilhuang007.razer.newevent.impl.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;

@Getter
@Setter
@AllArgsConstructor
public final class HurtRenderEvent extends CancellableEvent {

    private boolean oldDamage;

}
