package RazerOfficial.Razer.gg.module.impl.movement.noslow;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.player.BadPacketsComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.component.impl.render.NotificationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.SlowDownEvent;
import RazerOfficial.Razer.gg.event.impl.other.TeleportEvent;
import RazerOfficial.Razer.gg.module.impl.combat.KillAura;
import RazerOfficial.Razer.gg.module.impl.movement.NoSlow;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

import java.util.Objects;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class NewNCPNoSlow extends Mode<NoSlow> {

    private int disable;

    public NewNCPNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        this.disable++;
        if (InstanceAccess.mc.thePlayer.isUsingItem() && this.disable > 10 && !BadPacketsComponent.bad(false,
                true, true, false, false) && !(Objects.requireNonNull(SlotComponent.getItemStack()).getItem() instanceof ItemBow)  && Razer.INSTANCE.getModuleManager().get(KillAura.class).target == null) {
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if(Razer.INSTANCE.getModuleManager().get(KillAura.class).target == null) event.setCancelled(true);
    };


    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        this.disable = 0;
    };
    @Override
    public void onEnable() {
        NotificationComponent.post("Credit", "Thanks Auth for this No Slow", 5000);
    }
}