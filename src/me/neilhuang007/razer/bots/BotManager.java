package me.neilhuang007.razer.bots;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

/**
 * @author Auth
 * @since 3/03/2022
 */
public class BotManager extends ArrayList<Entity> {

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
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