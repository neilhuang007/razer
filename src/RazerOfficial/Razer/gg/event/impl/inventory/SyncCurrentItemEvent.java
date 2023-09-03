package RazerOfficial.Razer.gg.event.impl.inventory;


import RazerOfficial.Razer.gg.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SyncCurrentItemEvent implements Event {
    private int slot;
}