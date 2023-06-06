package me.neilhuang007.razer.module.impl.movement.noslow;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.component.impl.player.BadPacketsComponent;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.component.impl.render.NotificationComponent;
import me.neilhuang007.razer.module.impl.combat.KillAura;
import me.neilhuang007.razer.module.impl.movement.NoSlow;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.SlowDownEvent;
import me.neilhuang007.razer.newevent.impl.other.TeleportEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
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
                true, true, false, false) && !(Objects.requireNonNull(SlotComponent.getItemStack()).getItem() instanceof ItemBow)  && Client.INSTANCE.getModuleManager().get(KillAura.class).target == null) {
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        if(Client.INSTANCE.getModuleManager().get(KillAura.class).target == null) event.setCancelled(true);
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