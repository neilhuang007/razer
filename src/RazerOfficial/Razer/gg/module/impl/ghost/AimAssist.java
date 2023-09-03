package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.rotation.RotationUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

import java.util.List;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Rise
@ModuleInfo(name = "module.ghost.aimassist.name", description = "module.ghost.aimassist.description", category = Category.GHOST)
public class AimAssist extends Module {

    private final BoundsNumberValue strength = new BoundsNumberValue("AimAssist Strength", this, 30, 30, 1, 100, 1);
    private final BooleanValue onRotate = new BooleanValue("Only on mouse movement", this, true);
    private Vector2f rotations, lastRotations;

    @Override
    protected void onDisable() {
        EntityRenderer.mouseAddedX = EntityRenderer.mouseAddedY = 0;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        lastRotations = rotations;
        rotations = null;

        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) return;

        final List<EntityLivingBase> entities = Razer.INSTANCE.getTargetManager().getTargets(5);

        if (entities.isEmpty()) {
            return;
        }

        final EntityLivingBase target = entities.get(0);

        rotations = RotationUtil.calculate(target);
    };


    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (rotations == null || lastRotations == null ||
                (this.onRotate.getValue() && this.mc.mouseHelper.deltaX == 0 && this.mc.mouseHelper.deltaY == 0)) {
            return;
        }

        Vector2f rotations = new Vector2f(this.lastRotations.x + (this.rotations.x - this.lastRotations.x) * mc.timer.renderPartialTicks, 0);
        final float strength = (float) MathUtil.getRandom(this.strength.getValue().floatValue(), this.strength.getSecondValue().floatValue());

        final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float gcd = f * f * f * 8.0F;

        int i = mc.gameSettings.invertMouse ? -1 : 1;
        float f2 = this.mc.mouseHelper.deltaX + ((rotations.x - mc.thePlayer.rotationYaw) * (strength / 100) -
                this.mc.mouseHelper.deltaX) * gcd;
        float f3 = this.mc.mouseHelper.deltaY - this.mc.mouseHelper.deltaY * gcd;

        this.mc.thePlayer.setAngles(f2, f3 * (float) i);
    };
}