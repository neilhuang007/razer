package RazerOfficial.Razer.gg.module.impl.combat.criticals;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.AttackEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Criticals;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import util.time.StopWatch;

public final class EditCriticals extends Mode<Criticals> {

    private final NumberValue delay = new NumberValue("Delay", this, 500, 0, 1000, 50);

    private final double[] VALUES = new double[]{0.0005D, 0.0001D};
    private final StopWatch stopwatch = new StopWatch();

    private boolean attacked;
    private int ticks;

    public EditCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.onGround && attacked) {
            ticks++;

            switch (ticks) {
                case 1: {
                    event.setPosY(event.getPosY() + VALUES[0]);
                    break;
                }

                case 2: {
                    event.setPosY(event.getPosY() + VALUES[1]);
                    attacked = false;
                    break;
                }
            }

            event.setOnGround(false);
        } else {
            attacked = false;
            ticks = 0;
        }
    };

    @EventLink()
    public final Listener<AttackEvent> onAttackEvent = event -> {
        if (InstanceAccess.mc.thePlayer.onGround && !InstanceAccess.mc.thePlayer.isOnLadder() && stopwatch.finished(delay.getValue().longValue())) {
            InstanceAccess.mc.thePlayer.onCriticalHit(event.getTarget());

            stopwatch.reset();
            attacked = true;
        }
    };
}
