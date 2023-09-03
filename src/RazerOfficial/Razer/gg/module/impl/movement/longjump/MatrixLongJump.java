package RazerOfficial.Razer.gg.module.impl.movement.longjump;

import RazerOfficial.Razer.gg.component.impl.player.BlinkComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.movement.LongJump;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;

/**
 * @author Alan, (Nicklas Implemtentet cause yes)
 * @since 08.04.2022
 */
public class MatrixLongJump extends Mode<LongJump> {

    private double lastMotion;
    private int ticks;

    public MatrixLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (!InstanceAccess.mc.thePlayer.onGround)
            ticks++;
        if (InstanceAccess.mc.thePlayer.onGround)
            InstanceAccess.mc.thePlayer.jump();
        if (ticks % 12 == 0 || InstanceAccess.mc.thePlayer.isCollidedVertically)
            lastMotion = InstanceAccess.mc.thePlayer.motionY;
        InstanceAccess.mc.thePlayer.motionY = lastMotion;
        if (InstanceAccess.mc.thePlayer.motionY < 0.1)
            getModule(LongJump.class).setEnabled(false);
    };

    @Override
    public void onEnable() {
        this.ticks = 0;
        this.lastMotion = 0;
        BlinkComponent.blinking = true;
    }

    @Override
    public void onDisable() {
        BlinkComponent.blinking = false;
    }
}