package me.neilhuang007.razer.newevent.impl.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;

import java.util.List;

@Getter
@AllArgsConstructor
public final class ServerKickEvent implements Event {
    public List<String> message;
}