package RazerOfficial.Razer.gg.event.impl.other;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.DamageSource;


@Getter
@Setter
@AllArgsConstructor
public class HurtEvent extends CancellableEvent {
    final DamageSource source;
    float amount;
}
