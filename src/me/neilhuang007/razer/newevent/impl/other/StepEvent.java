package me.neilhuang007.razer.newevent.impl.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;

@Getter
@AllArgsConstructor
public final class StepEvent implements Event {

    private double height;
}
