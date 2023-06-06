package me.neilhuang007.razer.module.impl.movement.noslow;

import me.neilhuang007.razer.component.impl.hypixel.InventoryDeSyncComponent;
import me.neilhuang007.razer.component.impl.player.SlotComponent;
import me.neilhuang007.razer.module.impl.movement.NoSlow;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PostMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.SlowDownEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class OldIntaveNoSlow extends Mode<NoSlow> {

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
//        InventoryDeSyncComponent.setActive("/booster");

        if (mc.thePlayer.isUsingItem()) {
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex() % 8 + 1));
            PacketUtil.send(new C09PacketHeldItemChange(SlotComponent.getItemIndex()));
        }
    };

    @EventLink
    public final Listener<PostMotionEvent> onPostMotion = event -> {
        if (mc.thePlayer.isUsingItem() && InventoryDeSyncComponent.isDeSynced()) {
            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
        }
    };

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };

    public OldIntaveNoSlow(String name, NoSlow parent) {
        super(name, parent);
    }
}