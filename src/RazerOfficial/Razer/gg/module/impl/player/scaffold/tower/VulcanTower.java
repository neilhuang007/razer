package RazerOfficial.Razer.gg.module.impl.player.scaffold.tower;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.Scaffold;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class VulcanTower extends Mode<Scaffold> {

    public VulcanTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2) && mc.thePlayer.offGroundTicks > 3) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[mc.thePlayer.inventory.currentItem];

            if (itemStack == null || (itemStack.stackSize > 2)) {
               PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));
            }
            mc.thePlayer.motionY = 0.42F;
        }
    };
}
