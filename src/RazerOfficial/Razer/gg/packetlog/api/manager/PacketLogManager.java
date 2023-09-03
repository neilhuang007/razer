package RazerOfficial.Razer.gg.packetlog.api.manager;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.packetlog.Check;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;

import java.util.ArrayList;

/**
 * @author Alan
 * @since 10/19/2021
 */
public final class PacketLogManager extends ArrayList<Check> implements InstanceAccess {
    public boolean packetLogging;

    public void init() {
        Razer.INSTANCE.getEventBus().register(this);
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