package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.impl.player.PacketlessDamageComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.longjump.*;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

/**
 * @author Auth
 * @since 3/02/2022
 */
@Razer
@ModuleInfo(name = "module.movement.longjump.name", description = "module.movement.longjump.description", category = Category.MOVEMENT)
public class LongJump extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaLongJump("Vanilla", this))
            .add(new NCPLongJump("NCP", this))
            .add(new WatchdogLongJump("Watchdog", this))
            .add(new VulcanLongJump("Vulcan", this))
            .add(new ExtremeCraftLongJump("Extreme Craft", this))
            .add(new MatrixLongJump("Matrix", this))
            .add(new FireBallLongJump("Fire Ball", this))
            .setDefault("Vanilla");

    private final BooleanValue autoDisable = new BooleanValue("Auto Disable", this, true);
    private final BooleanValue fakeDamage = new BooleanValue("Fake Damage", this, false);

    private boolean inAir;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (autoDisable.getValue() && inAir && mc.thePlayer.onGround) {
            this.toggle();
        }

        inAir = !mc.thePlayer.onGround && !PacketlessDamageComponent.isActive();
    };

    @Override
    protected void onEnable() {
        if (fakeDamage.getValue() && mc.thePlayer.ticksExisted > 1) {
            PlayerUtil.fakeDamage();
        }
    }

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        inAir = false;
    }
}
