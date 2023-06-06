package me.neilhuang007.razer.module.impl.ghost;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.player.RotationComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.newevent.impl.render.MouseOverEvent;
import me.neilhuang007.razer.util.RayCastUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.reach.name", description = "module.ghost.reach.description", category = Category.GHOST)
public class Reach extends Module {

    public final NumberValue range = new NumberValue("Range", this, 4, 3, 6, 0.1);
    private final NumberValue bufferDecrease = new NumberValue("Buffer Decrease", this, 1, 0.1, 10, 0.1, () -> !this.bufferAbuse.getValue());
    private final NumberValue maxBuffer = new NumberValue("Max Buffer", this, 5, 1, 200, 1, () -> !this.bufferAbuse.getValue());
    private final BooleanValue bufferAbuse = new BooleanValue("Buffer Abuse", this, false);

    private int lastId, attackTicks;
    private double combo;
    private int exempt;


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.attackTicks++;
        exempt--;
    };

    @EventLink()
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        if (Mouse.isButtonDown(1)) {
            exempt = 1;
        }

        if (exempt > 0) return;

        event.setRange(this.range.getValue().doubleValue());
    };

    @EventLink()
    public final Listener<AttackEvent> onAttackEvent = event -> {

        final Entity entity = event.getTarget();

        if (this.bufferAbuse.getValue()) {
            if (RayCastUtil.rayCast(RotationComponent.rotations, 3.0D).typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
                if ((this.attackTicks > 9 || entity.getEntityId() != this.lastId) && this.combo < this.maxBuffer.getValue().intValue()) {
                    this.combo++;
                } else {
                    event.setCancelled(true);
                }
            } else {
                this.combo = Math.max(0, this.combo - this.bufferDecrease.getValue().doubleValue());
            }
        } else {
            this.combo = 0;
        }

        this.lastId = entity.getEntityId();
        this.attackTicks = 0;
    };
}