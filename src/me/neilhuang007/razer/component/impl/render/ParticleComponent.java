package me.neilhuang007.razer.component.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.Priorities;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.Render2DEvent;
import me.neilhuang007.razer.util.render.particle.Particle;

import java.util.concurrent.ConcurrentLinkedQueue;

@Rise
public class ParticleComponent extends Component {

    public static ConcurrentLinkedQueue<Particle> particles = new ConcurrentLinkedQueue<>();
    public static int rendered;

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2DEvent = event -> {
        if (particles.isEmpty()) return;
        NORMAL_POST_RENDER_RUNNABLES.add(ParticleComponent::render);
    };

    public static void render() {
        if (mc.ingameGUI.frame != rendered) {
            particles.forEach(particle -> {
                particle.render();

                if (particle.time.getElapsedTime() > 50 * 3 * 20) {
                    particles.remove(particle);
                }
            });

            threadPool.execute(() -> {
                particles.forEach(Particle::update);
            });

            rendered = mc.ingameGUI.frame;
        }
    }

    public static void add(final Particle particle) {
        particles.add(particle);
    }
}
