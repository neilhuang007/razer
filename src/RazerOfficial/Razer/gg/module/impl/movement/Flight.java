package RazerOfficial.Razer.gg.module.impl.movement;


import RazerOfficial.Razer.gg.api.Razer;
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
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import net.minecraft.entity.boss.EntityDragon;

import javax.vecmath.Vector3d;

/**
 * @author Auth (implementation)
 * @since 18/11/2021
 */

@Razer
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
        if (fakeDamage.getValue() && mc.thePlayer.ticksExisted > 1) {
            PlayerUtil.fakeDamage();
        }

        teleported = false;
    }

    @Override
    protected void onDisable() {
        if (entityDragon != null) {
            mc.theWorld.removeEntity(entityDragon);
            entityDragon = null;
        }

        mc.timer.timerSpeed = 1.0F;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (viewBobbing.getValue()) {
            mc.thePlayer.cameraYaw = 0.1F;
        }

        if (smoothCamera.getValue()) {
            SmoothCameraComponent.setY();
        }
    };

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (dragon.getValue()) {
            if (entityDragon == null) {
                entityDragon = new EntityDragon(mc.theWorld);
                mc.theWorld.addEntityToWorld(-1, entityDragon);
                RazerOfficial.Razer.gg.Razer.INSTANCE.getBotManager().add(entityDragon);
            }

            final Vector3d position = new Vector3d(
                    mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * mc.timer.renderPartialTicks,
                    mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * mc.timer.renderPartialTicks,
                    mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * mc.timer.renderPartialTicks
            );

            entityDragon.setPositionAndRotation(position.x, position.y - 3, position.z,
                    mc.thePlayer.rotationYaw - 180, mc.thePlayer.rotationPitch);
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