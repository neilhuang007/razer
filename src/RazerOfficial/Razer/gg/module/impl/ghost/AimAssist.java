package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.component.impl.render.ESPComponent;
import RazerOfficial.Razer.gg.component.impl.render.espcomponent.api.ESPColor;
import RazerOfficial.Razer.gg.component.impl.render.espcomponent.impl.SigmaRing;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.player.MouseOverEntityEvent;
import RazerOfficial.Razer.gg.event.impl.render.MouseOverEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.other.Timer;
import RazerOfficial.Razer.gg.util.RayCastUtil;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.util.render.ColorUtil;
import RazerOfficial.Razer.gg.util.render.RenderUtil;
import RazerOfficial.Razer.gg.util.rotation.RotationUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.*;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.optifine.util.MathUtils;
import org.codehaus.commons.nullanalysis.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Alan
 * @since 29/01/2021
 */

@Razer
@ModuleInfo(name = "module.ghost.aimassist.name", description = "module.ghost.aimassist.description", category = Category.GHOST)
public class AimAssist extends Module {

    private final Comparator<Entity> angleComparator = Comparator.comparingDouble(entity -> RotationUtil.getRotationsNeeded(entity)[0]);

    private final NumberValue Range = new NumberValue("Aim Range",this,5,3,10,1);

    public final ModeValue TargetPrority = new ModeValue("Target Prority", this)
            .add(new SubMode("Clip"))
            .add(new SubMode("Health"))
            .add(new SubMode("Distance"))
            .add(new SubMode("Angle"))
            .setDefault("Clip");

    private final BooleanValue Weapons = new BooleanValue("Only Weapons",this,false);

    private final BooleanValue View = new BooleanValue("In View",this,true);

    private final BoundsNumberValue HorizontalStrength = new BoundsNumberValue("Horizontal Strength", this, 15, 45, 1, 100, 1);
    private final BoundsNumberValue VerticalStrength = new BoundsNumberValue("Vertical Strength", this, 15, 45, 1, 100, 1);

    private final NumberValue PitchOffset = new NumberValue("Above or below waist",this,0.15, -1.7, 0.25, 0.050D);


    private final BooleanValue Render = new BooleanValue("Render Target", this, true);

    private final ColorValue ESPcolor = new ColorValue("ESP Color",this, Color.WHITE, () -> !Render.getValue());

    Entity target;

    private float randomYaw, randomPitch;



    // do render
    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Color color = Color.WHITE;
        ESPComponent.add(new SigmaRing(new ESPColor(color, color, color)));

        if(!TargetPrority.getValue().getName().contains("Clip")){
            target = getTargets();
        }
    };



    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if(TargetPrority.getValue().getName().contains("Clip")){
            try{
                target = PlayerUtil.getMouseOver(1,Range.getValue().doubleValue());
            }catch (NullPointerException e){
                // just catch just in case
            }
        }
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        Vector2f rotations;
        target = getTargets();
        if(target != null){
            if(!TargetPrority.getValue().getName().contains("Clip")){
                target = getTargets();
            }

            try{
                final Vector2f targetRotations = RotationUtil.calculate(target);
                double n = PlayerUtil.fovFromEntity(target);

                double complimentSpeedX = n
                        * (ThreadLocalRandom.current().nextDouble(HorizontalStrength.getSecondValue().doubleValue() - 1.47328,
                        HorizontalStrength.getSecondValue().doubleValue() + 2.48293) / 100);
                float valX = (float) (-(complimentSpeedX + (n / (101.0D - (float) ThreadLocalRandom.current()
                        .nextDouble(HorizontalStrength.getValue().doubleValue() - 4.723847, HorizontalStrength.getValue().doubleValue())))));

                double ry = mc.thePlayer.rotationYaw;
                // you want to handle a variable to smooth instead of adding in a function because that changes the yaw and becomes very weird
                //mc.thePlayer.rotationYaw += valX;
                rotations = RotationUtil.smooth(new Vector2f((float) ry, mc.thePlayer.rotationPitch),(new Vector2f((float) ry+valX, mc.thePlayer.rotationPitch)),MathUtil.getRandom(HorizontalStrength.getValue().doubleValue(),HorizontalStrength.getSecondValue().doubleValue()));
                mc.thePlayer.rotationYaw = rotations.x;

                double complimentSpeed = PlayerUtil.PitchFromEntity(target,
                        (float) PitchOffset.getValue().doubleValue())
                        * (ThreadLocalRandom.current().nextDouble(VerticalStrength.getSecondValue().doubleValue() - 1.47328,
                        VerticalStrength.getSecondValue().doubleValue() + 2.48293) / 100);

                float val = (float) (-(complimentSpeed
                        + (n / (101.0D - (float) ThreadLocalRandom.current()
                        .nextDouble(VerticalStrength.getValue().doubleValue() - 4.723847,
                                VerticalStrength.getValue().doubleValue())))));

                mc.thePlayer.rotationPitch += val;


//
//                // rotations that bypasses checks
                randomiseTargetRotations();
                targetRotations.x += randomYaw;
                targetRotations.y += randomPitch;
                RotationComponent.setRotations(targetRotations, MathUtil.getRandom(HorizontalStrength.getSecondValue().doubleValue(),HorizontalStrength.getValue().doubleValue()),MovementFix.NORMAL);
                RotationComponent.smooth();
            }catch (NullPointerException e){
                // this is for when joining world
            }



        }
    };

    public Entity getTargets(){
        // define targets first to eliminate any null pointer exceptions
        List<Entity> targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue());
        if(View.getValue()){
            targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue()).stream()
                    .filter(entity -> RayCastUtil.inView(entity))
                    .collect(Collectors.toList());
        }
        if(TargetPrority.getValue().getName().contains("Health")){
            targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue()).stream()
                    .sorted(Comparator.comparingDouble(o -> ((AbstractClientPlayer) o).getHealth()).reversed())
                    .collect(Collectors.toList());
        }
        if(TargetPrority.getValue().getName().contains("Distance")) {
            targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue()).stream()
                    .sorted(Comparator.comparingDouble(o -> mc.thePlayer.getDistanceToEntity((Entity) o)).reversed())
                    .collect(Collectors.toList());
        }
        if(TargetPrority.getValue().getName().contains("Angle")) {
            targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue());
            targets.sort(this.angleComparator);
        }

        if(!targets.isEmpty()){
            return targets.get(0);
        }else{
            return null;
        }
    };

    // render part
    @EventLink()
    public final Listener<Render3DEvent> onrender3D = event -> {
        if(Render.getValue()){
            final float partialTicks = mc.timer.renderPartialTicks;

            EntityLivingBase player = (EntityLivingBase) getTargets();
            if(player != null){
                final Color color = ESPcolor.getValue();

                if (mc.getRenderManager() == null || player == null) return;

                final double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks - (mc.getRenderManager()).renderPosX;
                final double y = player.prevPosY + (player.posY - player.prevPosY) * partialTicks + Math.sin(System.currentTimeMillis() / 2E+2) + 1 - (mc.getRenderManager()).renderPosY;
                final double z = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks - (mc.getRenderManager()).renderPosZ;

                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glEnable(2832);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glHint(3154, 4354);
                GL11.glHint(3155, 4354);
                GL11.glHint(3153, 4354);
                GL11.glDepthMask(false);
                GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GlStateManager.disableCull();
                GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

                for (float i = 0; i <= Math.PI * 2 + ((Math.PI * 2) / 32.F); i += (Math.PI * 2) / 32.F) {
                    double vecX = x + 0.67 * Math.cos(i);
                    double vecZ = z + 0.67 * Math.sin(i);

                    RenderUtil.color(ColorUtil.withAlpha(color, (int) (255 * 0.25)));
                    GL11.glVertex3d(vecX, y, vecZ);
                }

                for (float i = 0; i <= Math.PI * 2 + (Math.PI * 2) / 32.F; i += (Math.PI * 2) / 32.F) {
                    double vecX = x + 0.67 * Math.cos(i);
                    double vecZ = z + 0.67 * Math.sin(i);

                    RenderUtil.color(ColorUtil.withAlpha(color, (int) (255 * 0.25)));
                    GL11.glVertex3d(vecX, y, vecZ);

                    RenderUtil.color(ColorUtil.withAlpha(color, 0));
                    GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 2E+2) / 2.0F, vecZ);
                }

                GL11.glEnd();
                GL11.glShadeModel(GL11.GL_FLAT);
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
                GlStateManager.enableCull();
                GL11.glDisable(2848);
                GL11.glDisable(2848);
                GL11.glEnable(2832);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
                RenderUtil.color(Color.WHITE);
            }
        }
    };

    private void drawCircle(final Entity entity, final double rad, final int color, final boolean shade) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        if (shade) GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableCull();
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY) + Math.sin(System.currentTimeMillis() / 2E+2) + 1;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;

        Color c;

        for (float i = 0; i < Math.PI * 2; i += Math.PI * 2 / 64.F) {
            final double vecX = x + rad * Math.cos(i);
            final double vecZ = z + rad * Math.sin(i);

            c = Color.WHITE;

            if (shade) {
                GL11.glColor4f(c.getRed() / 255.F,
                        c.getGreen() / 255.F,
                        c.getBlue() / 255.F,
                        0
                );
                GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 2E+2) / 2.0F, vecZ);
                GL11.glColor4f(c.getRed() / 255.F,
                        c.getGreen() / 255.F,
                        c.getBlue() / 255.F,
                        0.85F
                );
            }
            GL11.glVertex3d(vecX, y, vecZ);
        }

        GL11.glEnd();
        if (shade) GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.enableCull();
        GL11.glDisable(2848);
        GL11.glDisable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
    }

    /*
     * Randomising rotation target to simulate legit players
     */
    private void randomiseTargetRotations() {
        randomYaw += (float) (Math.random() * 0.2 - 0.1);
        randomPitch += (float) (Math.random() - 0.5f) * 2;
    }

    private float[] smoothAngle(float[] dst, float[] src) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        smoothedAngle = MathUtil.constrainAngle(smoothedAngle);
        smoothedAngle[0] = (float) (src[0] - smoothedAngle[0] / 100 * MathUtil.getRandom(14, 24));
        smoothedAngle[1] = (float) (src[1] - smoothedAngle[1] / 100 * MathUtil.getRandom(3, 8));
        return smoothedAngle;
    }
    private float[] getRotationsToEnt(Entity ent, EntityPlayerSP playerSP) {
        final double differenceX = ent.posX - playerSP.posX;
        final double differenceY = (ent.posY + ent.height) - (playerSP.posY + playerSP.height);
        final double differenceZ = ent.posZ - playerSP.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }
}