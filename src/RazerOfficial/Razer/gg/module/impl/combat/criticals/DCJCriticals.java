package RazerOfficial.Razer.gg.module.impl.combat.criticals;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.player.AttackEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Criticals;
import RazerOfficial.Razer.gg.value.Mode;

public class DCJCriticals extends Mode<Criticals> {
    public DCJCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<AttackEvent> onAttack = attackEvent -> {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.10000000000713413;
            mc.thePlayer.fallDistance = 0.1f;
            mc.thePlayer.onGround = false;
        }
    };
}
