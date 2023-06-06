package me.neilhuang007.razer.component.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.component.impl.render.espcomponent.api.ESP;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import me.neilhuang007.razer.newevent.impl.render.LimitedRender2DEvent;
import me.neilhuang007.razer.newevent.impl.render.Render3DEvent;

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
