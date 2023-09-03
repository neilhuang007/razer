package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;

@Getter
@Setter
@AllArgsConstructor
public final class AttackEvent extends CancellableEvent {
    private Entity target;
}