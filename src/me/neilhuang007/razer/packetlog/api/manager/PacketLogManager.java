package me.neilhuang007.razer.packetlog.api.manager;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.packetlog.Check;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;

import java.util.ArrayList;

/**
 * @author Alan
 * @since 10/19/2021
 */
public final class PacketLogManager extends ArrayList<Check> implements InstanceAccess {
    public boolean packetLogging;

    public void init() {
        Client.INSTANCE.getEventBus().register(this);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mc.thePlayer.ticksExisted % 20 != 0) return;

        threadPool.execute(() -> {
            boolean detected = false;

            for (Check check : this) {
                if (check.run()) {
                    detected = true;
                    break;
                }
            }

            packetLogging = detected;
        });
    };
}