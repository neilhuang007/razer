package RazerOfficial.Razer.gg.component.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.component.impl.render.espcomponent.api.ESP;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
import RazerOfficial.Razer.gg.event.impl.render.LimitedRender2DEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

@Rise
public class ESPComponent extends Component {

    public static ConcurrentLinkedQueue<ESP> esps = new ConcurrentLinkedQueue<>();

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<LimitedRender2DEvent> onLimitedRender2D = event -> {

        if (esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render2D);
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (esps == null || esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render3D);
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        threadPool.execute(() -> {
            for (ESP esp1 : esps) {
                esp1.updateTargets();

                if (esp1.tick + 2 < mc.thePlayer.ticksExisted) {
                    esps.remove(esp1);
                }
            }
        });
    };

    public static void add(ESP esp) {
        threadPool.execute(() -> {
            boolean modified = false;
            for (ESP esp1 : esps) {
                if (esp.getClass().getSimpleName().equals(esp1.getClass().getSimpleName())) {
                    esp1.espColor = esp.espColor;
                    esp1.tick = mc.thePlayer.ticksExisted;;
                    modified = true;
                }
            }

            if (!modified) {
                esps.add(esp);
            }
        });
    }

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        esps.clear();
    };
}
