package me.neilhuang007.razer.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.interfaces.Toggleable;

import java.util.ArrayList;
import java.util.List;

/**
 * Rewritten from Patricks old version to be less retarded
 * @author Hazsi
 * @since 10/10/2022
 */
@Getter
@RequiredArgsConstructor
public abstract class Mode<T> implements InstanceAccess, Toggleable {
    private final String name;
    private final T parent;
    private final List<Value<?>> values = new ArrayList<>();

    public final void register() {
        Client.INSTANCE.getEventBus().register(this);
        this.onEnable();
    }

    public final void unregister() {
        Client.INSTANCE.getEventBus().unregister(this);
        this.onDisable();
    }

    @Override
    public void toggle() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}