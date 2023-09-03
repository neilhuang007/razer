package RazerOfficial.Razer.gg.module.impl.render;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.render.ProjectionComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.AttackEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.render.targetinfo.ModernTargetInfo;
import RazerOfficial.Razer.gg.util.vector.Vector2d;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.DragValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import util.time.StopWatch;

import javax.vecmath.Vector4d;

/**
 * @author Alan
 * @since 10/19/2021
 */

@Rise
@ModuleInfo(name = "module.render.targetinfo.name", description = "module.render.targetinfo.description", category = Category.RENDER)
public final class TargetInfo extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new ModernTargetInfo("Modern", this))
            .setDefault("Modern");

    public final DragValue positionValue = new DragValue("Position", this, new Vector2d(200, 200));
    public final BooleanValue followPlayer = new BooleanValue("Follow Player", this, false);

    public Vector2d position = new Vector2d(0, 0);
    public Entity target;
    public double distanceSq;
    public boolean inWorld;
    public StopWatch stopwatch = new StopWatch();

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

//        target = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChat) {
            stopwatch.reset();
            target = mc.thePlayer;
        }

        if (target == null) {
            inWorld = false;
            return;
        }

        distanceSq = mc.thePlayer.getDistanceSqToEntity(target);
        inWorld = mc.theWorld.loadedEntityList.contains(target);
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {

        if (event.getTarget() instanceof AbstractClientPlayer) {
            target = event.getTarget();
            stopwatch.reset();
        }
    };


    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        if (target == null) {
            return;
        }

        if (this.followPlayer.getValue()) {
            Vector4d position = ProjectionComponent.get(target);

            if (position == null) return;

            this.position.x = position.z;
            this.position.y = position.w - (position.w - position.y) / 2 - this.positionValue.scale.y / 2f;
        } else {
            this.position = positionValue.position;
        }
    };
}