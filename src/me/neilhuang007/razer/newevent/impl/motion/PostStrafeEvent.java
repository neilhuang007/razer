package me.neilhuang007.razer.newevent.impl.motion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

@Getter
@Setter
@AllArgsConstructor
public final class PostStrafeEvent extends CancellableEvent implements InstanceAccess {

}
