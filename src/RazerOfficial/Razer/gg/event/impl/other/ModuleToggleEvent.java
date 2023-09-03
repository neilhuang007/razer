package RazerOfficial.Razer.gg.event.impl.other;


import RazerOfficial.Razer.gg.event.Event;
import RazerOfficial.Razer.gg.module.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;
}