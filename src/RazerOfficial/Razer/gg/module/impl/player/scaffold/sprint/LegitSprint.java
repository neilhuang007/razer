package RazerOfficial.Razer.gg.module.impl.player.scaffold.sprint;

import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.util.MathHelper;

public class LegitSprint extends Mode<Scaffold> {

    public LegitSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (Math.abs(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) - MathHelper.wrapAngleTo180_float(RotationComponent.rotations.x)) > 90) {
            mc.gameSettings.keyBindSprint.setPressed(false);
            mc.thePlayer.setSprinting(false);
        }
    };
}
