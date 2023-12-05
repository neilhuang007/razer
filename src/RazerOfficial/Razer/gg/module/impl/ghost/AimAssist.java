package RazerOfficial.Razer.gg.module.impl.ghost;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.player.MouseOverEntityEvent;
import RazerOfficial.Razer.gg.event.impl.render.MouseOverEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render2DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.other.Timer;
import RazerOfficial.Razer.gg.util.RayCastUtil;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.util.rotation.RotationUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import org.codehaus.commons.nullanalysis.NotNull;
import org.lwjgl.input.Mouse;

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

    private final NumberValue Range = new NumberValue("Aim Range",this,5,3,10,1);

    private final BooleanValue Clip = new BooleanValue("Clip to player",this,true);

    private final BooleanValue Weapons = new BooleanValue("Only Weapons",this,true);

    private final BooleanValue View = new BooleanValue("In View",this,true);

    private final BoundsNumberValue HorizontalStrength = new BoundsNumberValue("Horizontal Strength", this, 15, 45, 1, 100, 1);
    private final BoundsNumberValue VerticalStrength = new BoundsNumberValue("Vertical Strength", this, 15, 45, 1, 100, 1);

    private final NumberValue PitchOffset = new NumberValue("Above or below waist",this,0.15, -1.7, 0.25, 0.050D);

    Entity target;




    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        if(Clip.getValue()){
            try{
                target = PlayerUtil.getMouseOver(1,10000000);
                ChatUtil.display(target);
            }catch (NullPointerException e){
                // just catch just in case
            }

        }
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        // define targets first to eliminate any null pointer exceptions
        List<Entity> targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue());
        if(View.getValue()){
            targets = RazerOfficial.Razer.gg.Razer.INSTANCE.getTargetManager().getTargets(Range.getValue().intValue()).stream()
                    .filter(entity -> RayCastUtil.inView(entity))
                    .collect(Collectors.toList());
        }

        if(!targets.isEmpty()){
            if(!Clip.getValue()){
                ChatUtil.display("Choosed target for you");
                Entity target = targets.get(0);
            }
            if(target != null){
                double n = PlayerUtil.fovFromEntity(target);
                // this is the weapon check here
                if(Weapons.getValue()){
                    // try catch to kill the null pointers while joining servers GENIUS!!!!
                    try{
                        if(!(this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword || this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)){
                            return;
                        }
                    }catch (NullPointerException e){
                        return;
                    }
                }
                double complimentSpeed = n
                        * (ThreadLocalRandom.current().nextDouble(HorizontalStrength.getSecondValue().doubleValue() - 1.47328,
                        HorizontalStrength.getSecondValue().doubleValue() + 2.48293) / 100);
                float val = (float) (-(complimentSpeed + (n / (101.0D - (float) ThreadLocalRandom.current()
                        .nextDouble(HorizontalStrength.getValue().doubleValue() - 4.723847, HorizontalStrength.getValue().doubleValue())))));
                mc.thePlayer.rotationYaw += val;

                //Bypass for anticheats with rotation checks xd - ok
                mc.thePlayer.rotationPitch += Math.random() * 0.2 - 0.1;
                mc.thePlayer.rotationPitch = Math.max(mc.thePlayer.rotationPitch, -90);
                mc.thePlayer.rotationPitch = Math.min(mc.thePlayer.rotationPitch, 90);
                // now the pitch
                double complimentYSpeed = PlayerUtil.PitchFromEntity(target,
                        (float) PitchOffset.getValue().doubleValue())
                        * (ThreadLocalRandom.current().nextDouble(VerticalStrength.getSecondValue().doubleValue() - 1.47328,
                        VerticalStrength.getSecondValue().doubleValue() + 2.48293) / 100);

                float valY = (float) (-(complimentYSpeed
                        + (n / (101.0D - (float) ThreadLocalRandom.current()
                        .nextDouble(VerticalStrength.getValue().doubleValue() - 4.723847,
                                VerticalStrength.getValue().doubleValue())))));

                mc.thePlayer.rotationPitch += valY;
                //Bypass for anticheats with rotation checks xd
                mc.thePlayer.rotationPitch += Math.random() * 0.2 - 0.1;
                mc.thePlayer.rotationPitch = Math.max(mc.thePlayer.rotationPitch, -90);
                mc.thePlayer.rotationPitch = Math.min(mc.thePlayer.rotationPitch, 90);
            }
        }
    };
}