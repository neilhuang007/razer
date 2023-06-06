package me.neilhuang007.razer.newevent.impl.other;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.newevent.Event;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;
}