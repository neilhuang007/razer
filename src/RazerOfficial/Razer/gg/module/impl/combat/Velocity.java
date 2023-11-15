package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.combat.velocity.*;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

import java.util.function.BooleanSupplier;

@Rise
@ModuleInfo(name = "module.combat.velocity.name", description = "module.combat.velocity.description" /* Sorry, Tecnio. */ /* Sorry Hazsi. */, category = Category.COMBAT)
public final class Velocity extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardVelocity("Standard", this))
            .add(new DelayVelocity("Delay", this))
            .add(new LegitVelocity("Legit", this))
            .add(new WatchdogVelocity("Watchdog", this))
            .add(new SleepVelocity("Cancel",this))
            .setDefault("Standard");



    public final BooleanValue onExplosion = new BooleanValue("Explosion Reduction", this, false);

    public final BooleanValue onSwing = new BooleanValue("On Swing", this, false);
    public final BooleanValue onSprint = new BooleanValue("On Sprint", this, false);

    //public final BooleanValue LagBackDetection = new BooleanValue("Lagback Detections", this, true,()-> mode.getValue().equals(mode.getDefaultValue()));




}
