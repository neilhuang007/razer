package RazerOfficial.Razer.gg.module.impl.movement;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.JumpEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.movement.jesus.GravityJesus;
import RazerOfficial.Razer.gg.module.impl.movement.jesus.KarhuJesus;
import RazerOfficial.Razer.gg.module.impl.movement.jesus.NCPJesus;
import RazerOfficial.Razer.gg.module.impl.movement.jesus.VanillaJesus;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;

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