package RazerOfficial.Razer.gg.bots;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

/**
 * @author Auth
 * @since 3/03/2022
 */
public class BotManager extends ArrayList<Entity> {

    public void init() {
        Razer.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        this.clear();
    };

    public boolean add(Entity entity) {
        if (!this.contains(entity)) super.add(entity);
        return false;
    }
}