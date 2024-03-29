package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.module.teleportaura.TeleportAuraComponent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.pathfinding.unlegit.Vec3;
import RazerOfficial.Razer.gg.value.impl.BoundsNumberValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import net.minecraft.entity.Entity;
import util.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @since 11/17/2022
 */

@Razer
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

    public List<Entity> attackedList = new ArrayList<>();
    public Entity target;
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