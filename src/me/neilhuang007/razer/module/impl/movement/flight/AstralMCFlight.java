package me.neilhuang007.razer.module.impl.movement.flight;

import me.neilhuang007.razer.module.impl.movement.Flight;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.TeleportEvent;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.MoveUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 03.07.2022
 */
public class AstralMCFlight extends Mode<Flight> {

    private NumberValue height = new NumberValue("Height", this, 1, 0.1, 10, 0.1);
    private NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 10, 0.1);

    public AstralMCFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("Place a block to fly");
    }

    @EventLink
    private Listener<TeleportEvent> teleport = event -> {
        mc.thePlayer.motionY = height.getValue().doubleValue();
        MoveUtil.strafe(speed.getValue().doubleValue());
        event.setCancelled(true);
        mc.thePlayer.setPosition(event.getPosX(), event.getPosY(), event.getPosZ());
        PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(event.getPosX(), event.getPosY(), event.getPosZ(), event.getYaw(), event.getPitch(), false));
    };

    @EventLink
    private Listener<PreMotionEvent> preMotion = event -> {
    };
}
