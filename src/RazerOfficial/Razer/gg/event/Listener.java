package RazerOfficial.Razer.gg.event;

@FunctionalInterface
public interface Listener<Event> {
    void call(Event event);
}