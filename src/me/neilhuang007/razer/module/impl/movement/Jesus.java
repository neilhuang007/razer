package me.neilhuang007.razer.module.impl.movement;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.module.impl.movement.jesus.GravityJesus;
import me.neilhuang007.razer.module.impl.movement.jesus.KarhuJesus;
import me.neilhuang007.razer.module.impl.movement.jesus.NCPJesus;
import me.neilhuang007.razer.module.impl.movement.jesus.VanillaJesus;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.JumpEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.ModeValue;

@Rise
@ModuleInfo(name = "module.movement.jesus.name", description = "module.movement.jesus.description", category = Category.MOVEMENT)
public class Jesus extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaJesus("Vanilla", this))
            .add(new GravityJesus("Gravity", this))
            .add(new KarhuJesus("Karhu", this))
            .add(new NCPJesus("NCP", this))
            .setDefault("Vanilla");

    private final BooleanValue allowJump = new BooleanValue("Allow Jump", this, true);

    @EventLink()
    public final Listener<JumpEvent> onJump = event -> {

        if (!allowJump.getValue() && PlayerUtil.onLiquid()) {
            event.setCancelled(true);
        }
    };
}