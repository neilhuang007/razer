package me.neilhuang007.razer.component.impl.event;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.newevent.impl.other.KillEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

@Rise
//@Priority(priority = -100) /* Must be run before all modules */
public class EntityKillEventComponent extends Component {

    Entity target = null;

    @EventLink(value = Priorities.LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        threadPool.execute(() -> {
            if (target != null && !mc.theWorld.loadedEntityList.contains(target)) {
                target = null;
                Client.INSTANCE.getEventBus().handle(new KillEvent(target));
            }
        });
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<AttackEvent> onAttackEvent = event -> {
        target = event.getTarget();
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        target = null;
    };
}
