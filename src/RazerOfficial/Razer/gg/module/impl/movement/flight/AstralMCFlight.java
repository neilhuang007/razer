package RazerOfficial.Razer.gg.module.impl.movement.flight;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.TeleportEvent;
import RazerOfficial.Razer.gg.module.impl.movement.Flight;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 03.07.2022
 */
public class AstralMCFlight extends Mode<Flight> {

    private NumberValue height = new NumberValue("Height", this, 1, 0.1, 10, 0.1);
    private NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 10, 0.1);
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

    public AstralMCFlight(String name, Flight parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        ChatUtil.display("Place a block to fly");
    }
}
