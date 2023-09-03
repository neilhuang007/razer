package RazerOfficial.Razer.gg.component.impl.event;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.AttackEvent;
import RazerOfficial.Razer.gg.event.impl.other.KillEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
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
                Razer.INSTANCE.getEventBus().handle(new KillEvent(target));
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
