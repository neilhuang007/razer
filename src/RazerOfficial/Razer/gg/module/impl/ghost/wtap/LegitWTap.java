package RazerOfficial.Razer.gg.module.impl.ghost.wtap;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.AttackEvent;
import RazerOfficial.Razer.gg.module.impl.ghost.WTap;
import RazerOfficial.Razer.gg.value.Mode;

public class LegitWTap extends Mode<WTap> {

    private boolean unsprint, wTap;

    public LegitWTap(String name, WTap parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        wTap = Math.random() * 100 < getParent().chance.getValue().doubleValue();

        if (!wTap) return;

        if (mc.thePlayer.isSprinting() || mc.gameSettings.keyBindSprint.isKeyDown()) {
            mc.gameSettings.keyBindSprint.setPressed(true);
            unsprint = true;
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (!wTap) return;

        if (unsprint) {
            mc.gameSettings.keyBindSprint.setPressed(false);
            unsprint = false;
        }
    };
}