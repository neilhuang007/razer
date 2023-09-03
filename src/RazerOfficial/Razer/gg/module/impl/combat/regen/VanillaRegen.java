package RazerOfficial.Razer.gg.module.impl.combat.regen;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Regen;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class VanillaRegen extends Mode<Regen> {

    private final NumberValue health = new NumberValue("Minimum Health", this, 15, 1, 20, 1);
    private final NumberValue packets = new NumberValue("Speed", this, 20, 1, 100, 1);

    public VanillaRegen(String name, Regen parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.getHealth() < this.health.getValue().floatValue()) {
            for (int i = 0; i < this.packets.getValue().intValue(); i++) {
                PacketUtil.send(new C03PacketPlayer(event.isOnGround()));
            }
        }
    };
}
