package me.neilhuang007.razer.newevent.impl.inventory;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.Event;

@Getter
@Setter
@AllArgsConstructor
public class SyncCurrentItemEvent implements Event {
    private int slot;
}