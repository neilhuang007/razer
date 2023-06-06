package me.neilhuang007.razer.module.impl.movement.longjump;

import me.neilhuang007.razer.module.impl.movement.LongJump;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.util.interfaces.InstanceAccess;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/**
 * @author Alan, (Nicklas Implemtentet cause yes)
 * @since 08.04.2022
 */
public class ExtremeCraftLongJump extends Mode<LongJump> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 2.5, 0.1);

    private boolean receivedDamage;

    public ExtremeCraftLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (!receivedDamage) {
            InstanceAccess.mc.timer.timerSpeed = 1.0F;
        }
    };


    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> p = event.getPacket();

        if (p instanceof S08PacketPlayerPosLook && receivedDamage) {
            event.setCancelled(true);
            receivedDamage = false;
        }
    };

    @Override
    public void onEnable() {
        receivedDamage = false;

        if (InstanceAccess.mc.thePlayer.onGround) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY, InstanceAccess.mc.thePlayer.posZ, true));
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY - 0.1F, InstanceAccess.mc.thePlayer.posZ, true));
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(InstanceAccess.mc.thePlayer.posX, InstanceAccess.mc.thePlayer.posY, InstanceAccess.mc.thePlayer.posZ, false));

            InstanceAccess.mc.timer.timerSpeed = 0.2F;

            MoveUtil.strafe(3F);

            InstanceAccess.mc.thePlayer.motionY = height.getValue().doubleValue();

            receivedDamage = true;
        }
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}