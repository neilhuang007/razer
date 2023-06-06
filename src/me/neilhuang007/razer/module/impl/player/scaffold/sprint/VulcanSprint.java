package me.neilhuang007.razer.module.impl.player.scaffold.sprint;

import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketSendEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class VulcanSprint extends Mode<Scaffold> {
    private final NumberValue speed = new NumberValue("Speed", this, 1.3, 0.9, 1.3, 0.05);

    private int time, block;

    public VulcanSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        if (time == 1) {
            PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.setSprinting(false);

        final double speed = this.speed.getValue().doubleValue();

        if (!mc.gameSettings.keyBindJump.isKeyDown() && speed > 0.9) {
            if (mc.thePlayer.onGroundTicks >= 2 && block <= 10) {
                MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * speed);

                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0.012500047683714;
            }
        }

        if (mc.thePlayer.onGround) {
            MoveUtil.strafe();
        }

        time++;
        block++;

        switch (time) {
            case 1:
                PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                break;

            case 10:
                time = 0;
                break;
        }
    };

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C08PacketPlayerBlockPlacement) {
            final C08PacketPlayerBlockPlacement wrapper = (C08PacketPlayerBlockPlacement) p;

            if (wrapper.getPlacedBlockDirection() != 255) {
                block = 0;
            }
        }
    };
}
