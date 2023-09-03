package RazerOfficial.Razer.gg.util.render.particle;

import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import util.time.StopWatch;

import java.awt.*;

public class Particle implements InstanceAccess {
    private final Vector2f position;
    private final Vector2f velocity;
    private final float scale;
    private final Color color;
    public StopWatch stopWatch = new StopWatch(), time = new StopWatch();
    public float alpha;

    public Particle(final Vector2f position, final Color color, final Vector2f velocity, final float scale) {
        this.position = position;
        this.color = color;
        this.velocity = velocity;
        this.scale = scale;
        stopWatch.reset();
        alpha = color.getAlpha();
    }

    public Particle(final Vector2f position, final Vector2f velocity) {
        this.position = position;
        this.color = ColorUtil.withAlpha(ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), Math.random()), (int) (Math.random() * 255));
        this.velocity = velocity;
        this.scale = (float) (2 + Math.random() * 3);
        stopWatch.reset();
        alpha = color.getAlpha();
    }

    public void render() {

        NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
            RenderUtil.polygon(position.x, position.y, scale, (int) Math.ceil(scale * Math.PI * 2), ColorUtil.withAlpha(color, (int) alpha * 2));
        });

        RenderUtil.polygon(position.x, position.y, scale, (int) Math.ceil(scale * Math.PI * 2), ColorUtil.withAlpha(color, (int) alpha));

    }

    public void update() {
        for (int i = 0; i <= stopWatch.getElapsedTime(); i++) {
            this.position.setX(this.position.getX() + this.velocity.getX() / 10f);
            this.position.setY(this.position.getY() + this.velocity.getY() / 10f);

            this.velocity.setX(this.velocity.getX() * 0.999f);
            this.velocity.setY(this.velocity.getY() * 0.999f);

//                this.velocity.setY(this.velocity.getY() + 0.005f);
        }

        alpha = Math.max(alpha - stopWatch.getElapsedTime() / 20f, 0);

        stopWatch.reset();
    }
}
