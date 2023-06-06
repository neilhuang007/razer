package me.neilhuang007.razer.module.impl.movement.longjump;

import me.neilhuang007.razer.component.impl.player.BlinkComponent;
import me.neilhuang007.razer.module.impl.movement.LongJump;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.value.Mode;

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