package RazerOfficial.Razer.gg.module.impl.movement.noslow;

import RazerOfficial.Razer.gg.component.impl.hypixel.InventoryDeSyncComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PostMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.SlowDownEvent;
import RazerOfficial.Razer.gg.module.impl.movement.NoSlow;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
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