package me.neilhuang007.razer.module.impl.combat.regen;

import me.neilhuang007.razer.module.impl.combat.Regen;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;
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
