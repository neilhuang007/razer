package RazerOfficial.Razer.gg.event.impl.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public class MouseOverEntityEvent {
    private Entity entity;
}
