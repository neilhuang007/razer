package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.step.*;
import me.neilhuang007.razer.value.impl.ModeValue;

@Rise
@ModuleInfo(name = "module.movement.step.name", description = "module.movement.step.description", category = Category.MOVEMENT)
public class Step extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaStep("Vanilla", this))
            .add(new WatchdogStep("Watchdog", this))
            .add(new NCPStep("NCP", this))
            .add(new NCPPacketLessStep("NCP Packetless", this))
            .add(new VulcanStep("Vulcan", this))
            .add(new MatrixStep("Matrix", this))
            .add(new JumpStep("Jump", this))
            .setDefault("Vanilla");
}