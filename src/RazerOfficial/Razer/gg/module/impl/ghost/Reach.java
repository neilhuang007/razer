package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.player.AttackEvent;
import RazerOfficial.Razer.gg.event.impl.render.MouseOverEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.RayCastUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Razer
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