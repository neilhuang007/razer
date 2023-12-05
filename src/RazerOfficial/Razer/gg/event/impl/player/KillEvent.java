package RazerOfficial.Razer.gg.event.impl.player;


import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public final class KillEvent implements Event {

    Entity entity;

}