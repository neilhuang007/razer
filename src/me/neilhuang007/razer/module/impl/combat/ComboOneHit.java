package me.neilhuang007.razer.module.impl.combat;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.other.AttackEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.play.client.C02PacketUseEntity;

@Rise
@ModuleInfo(name = "module.combat.comboonehit.name", description = "module.combat.comboonehit.description", category = Category.COMBAT)
public final class ComboOneHit extends Module {

    public final NumberValue packets = new NumberValue("Attack Packets", this, 50, 1, 1000, 25);

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        for (int i = 0; i < packets.getValue().intValue(); i++) {
            PacketUtil.send(new C02PacketUseEntity(event.getTarget(), C02PacketUseEntity.Action.ATTACK));
        }
    };
}
