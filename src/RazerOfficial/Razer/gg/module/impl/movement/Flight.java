package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.impl.render.SmoothCameraComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.AttackEvent;
import RazerOfficial.Razer.gg.event.impl.other.TeleportEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.flight.*;
import RazerOfficial.Razer.gg.module.impl.movement.flight.deprecated.ACRFlight;
import RazerOfficial.Razer.gg.module.impl.movement.flight.deprecated.DamageFlight;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import net.minecraft.entity.boss.EntityDragon;

import javax.vecmath.Vector3d;

/**
 * @author Auth (implementation)
 * @since 18/11/2021
 */

@Rise
@ModuleInfo(name = "module.movement.flight.name", description = "module.movement.flight.description", category = Category.MOVEMENT)
public class Flight extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaFlight("Vanilla", this))
            .add(new CreativeFlight("Creative", this))
            .add(new BlockDropFlight("Block Drop", this))
            .add(new AirWalkFlight("Air Walk", this))
            .add(new OldNCPFlight("Old NCP", this))
            .add(new FuncraftFlight("Funcraft", this))
            .add(new LatestNCPFlight("Latest NCP", this))
            .add(new VerusFlight("Verus", this))
            .add(new BlockFlight("Block", this))
            .add(new MMCFlight("MMC", this))
            .add(new BufferAbuseFlight("Buffer Abuse", this))
            .add(new ZoneCraftFlight("Zone Craft", this))
            .add(new SlimeNCPFlight("Slime NCP", this))
            .add(new AirJumpFlight("Air Jump", this))
            .add(new CubeCraftFlight("CubeCraft", this))
            .add(new MineLandFlight("MineLand", this))
            .add(new MineLandFlight("MineLand", this))
            .add(new VulcanFlight("Vulcan", this))
            .add(new GrimFlight("Grim", this))
            .add(new AstralMCFlight("AstralMC", this))
            .add(new UnKnownAC("Buzz", this))
            .add(new WatchdogFlight("Watchdog", this))
            .add(new DamageFlight("Damage (Deprecated)", this))
            .add(new ACRFlight("ACR (Deprecated)", this))
            .add(new AirJumpFlight("Spartan (Deprecated)", this))
            .add(new BufferAbuseFlight("Vicnix (Deprecated)", this))
            .setDefault("Vanilla");

    private final BooleanValue disableOnTeleport = new BooleanValue("Disable on Teleport", this, false);
    private final BooleanValue viewBobbing = new BooleanValue("View Bobbing", this, false);
    private final BooleanValue fakeDamage = new BooleanValue("Fake Damage", this, false);
    private final BooleanValue smoothCamera = new BooleanValue("Smooth Camera", this, false);
    private final BooleanValue dragon = new BooleanValue("Visual Dragon", this, false);

    private EntityDragon entityDragon;
    private boolean teleported;

    @Override
    protected void onEnable() {
        if (fakeDamage.getValue() && InstanceAccess.mc.thePlayer.ticksExisted > 1) {
            PlayerUtil.fakeDamage();
        }

        teleported = false;
    }

    @Override
    protected void onDisable() {
        if (entityDragon != null) {
            InstanceAccess.mc.theWorld.removeEntity(entityDragon);
            entityDragon = null;
        }

        InstanceAccess.mc.timer.timerSpeed = 1.0F;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (viewBobbing.getValue()) {
            InstanceAccess.mc.thePlayer.cameraYaw = 0.1F;
        }

        if (smoothCamera.getValue()) {
            SmoothCameraComponent.setY();
        }
    };

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (dragon.getValue()) {
            if (entityDragon == null) {
                entityDragon = new EntityDragon(InstanceAccess.mc.theWorld);
                InstanceAccess.mc.theWorld.addEntityToWorld(-1, entityDragon);
                Razer.INSTANCE.getBotManager().add(entityDragon);
            }

            final Vector3d position = new Vector3d(
                    InstanceAccess.mc.thePlayer.lastTickPosX + (InstanceAccess.mc.thePlayer.posX - InstanceAccess.mc.thePlayer.lastTickPosX) * InstanceAccess.mc.timer.renderPartialTicks,
                    InstanceAccess.mc.thePlayer.lastTickPosY + (InstanceAccess.mc.thePlayer.posY - InstanceAccess.mc.thePlayer.lastTickPosY) * InstanceAccess.mc.timer.renderPartialTicks,
                    InstanceAccess.mc.thePlayer.lastTickPosZ + (InstanceAccess.mc.thePlayer.posZ - InstanceAccess.mc.thePlayer.lastTickPosZ) * InstanceAccess.mc.timer.renderPartialTicks
            );

            entityDragon.setPositionAndRotation(position.x, position.y - 3, position.z,
                    InstanceAccess.mc.thePlayer.rotationYaw - 180, InstanceAccess.mc.thePlayer.rotationPitch);
        }
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        if (event.getTarget() == entityDragon) {
            event.setCancelled(true);
        }
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {

        if (disableOnTeleport.getValue()) {
            if ("Watchdog".equals(mode.getValue().getName())) {
                if (teleported) {
                    this.toggle();
                }

                teleported = true;
            } else {
                this.toggle();
            }
        }
    };
}