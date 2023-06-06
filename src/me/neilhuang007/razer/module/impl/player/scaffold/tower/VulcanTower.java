package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
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
