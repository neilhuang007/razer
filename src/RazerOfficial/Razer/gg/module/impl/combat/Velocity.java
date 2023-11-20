package RazerOfficial.Razer.gg.module.impl.combat;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.combat.velocity.*;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;

@Razer
@ModuleInfo(name = "module.combat.velocity.name", description = "module.combat.velocity.description" /* Sorry, Tecnio. */ /* Sorry Hazsi. */, category = Category.COMBAT)
public final class Velocity extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardVelocity("Standard", this))
            .add(new DelayVelocity("Delay", this))
            .add(new LegitVelocity("Legit", this))
            .add(new WatchdogVelocity("Watchdog", this))
            .add(new SleepVelocity("Cancel",this))
            .setDefault("Standard");


    public final BooleanValue LagBackDetection = new BooleanValue("Lagback Detections", this, true, () -> !mode.getValue().getName().contains("Standard"));
    // bruh the clickgui does not read the things properly in the mode value so has to write like this

    public final BooleanValue retoggle = new BooleanValue("retoggle", this, true,() -> !(mode.getValue().getName().contains("Standard") && LagBackDetection.getValue()));

    public final NumberValue RetoggleDelay = new NumberValue("Retoggle Delay(ms)", this, 0.5F,0.1,3,0.1,()-> !(mode.getValue().getName().contains("Standard") && LagBackDetection.getValue()));


    public final NumberValue LagBacks = new NumberValue("Lagbacks Disable", this, 0, 0, 50, 1, () -> !(mode.getValue().getName().contains("Standard") && LagBackDetection.getValue()));

    public final BooleanValue onExplosion = new BooleanValue("Explosion Reduction", this, false);

    public final BooleanValue onSwing = new BooleanValue("On Swing", this, false);
    public final BooleanValue onSprint = new BooleanValue("On Sprint", this, false);



}



