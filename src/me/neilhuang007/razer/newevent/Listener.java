package me.neilhuang007.razer.newevent;

@FunctionalInterface
public interface Listener<Event> {
    void call(Event event);
}