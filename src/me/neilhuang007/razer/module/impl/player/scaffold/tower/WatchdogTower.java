package me.neilhuang007.razer.module.impl.player.scaffold.tower;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.motion.StrafeEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class WatchdogTower extends Mode<Scaffold> {

    boolean jump, side;

    public WatchdogTower(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown() || !MoveUtil.isMoving()) {
            return;
        }

        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = MoveUtil.jumpMotion();
            mc.thePlayer.motionX *= .65;
            mc.thePlayer.motionZ *= .65;
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (!mc.gameSettings.keyBindJump.isKeyDown()) return;

    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        final Packet<?> packet = event.getPacket();

        if (mc.thePlayer.motionY > -0.0784000015258789 && !mc.thePlayer.isPotionActive(Potion.jump) && packet instanceof C08PacketPlayerBlockPlacement && MoveUtil.isMoving()) {
            final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

            if (wrapper.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4, mc.thePlayer.posZ))) {
                mc.thePlayer.motionY = -0.0784000015258789;
            }
        }
    };
}
