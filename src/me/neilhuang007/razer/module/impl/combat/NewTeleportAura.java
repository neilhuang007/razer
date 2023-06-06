package me.neilhuang007.razer.module.impl.combat;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.component.impl.module.teleportaura.TeleportAuraComponent;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.util.pathfinding.unlegit.Vec3;
import me.neilhuang007.razer.value.impl.BoundsNumberValue;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import me.neilhuang007.razer.value.impl.SubMode;
import net.minecraft.entity.EntityLivingBase;
import util.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @since 11/17/2022
 */

@Rise
@ModuleInfo(name = "module.combat.newteleportaura.name", description = "module.combat.newteleportaura.description", category = Category.COMBAT)
public final class NewTeleportAura extends Module {
    public final NumberValue range = new NumberValue("Range", this, 30, 6, 100, 1);

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Single"))
            .add(new SubMode("Switch"))
            .setDefault("Single");

    public final ModeValue type = new ModeValue("Packet Type", this)
            .add(new SubMode("Send"))
            .add(new SubMode("Edit"))
            .setDefault("Send");

    public final BoundsNumberValue cps = new BoundsNumberValue("CPS", this, 10, 15, 1, 20, 1);

    public List<EntityLivingBase> attackedList = new ArrayList<>();
    public EntityLivingBase target;
    public Vec3 targetPosition, position;
    public boolean attacked;
    public long nextSwing;
    public StopWatch stopWatch = new StopWatch();

    @Override
    protected void onEnable() {
        position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        TeleportAuraComponent.enabled = true;
        attacked = true;
    }
}