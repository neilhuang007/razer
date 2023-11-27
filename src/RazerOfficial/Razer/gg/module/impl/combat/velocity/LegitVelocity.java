package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import util.time.StopWatch;

import java.util.Random;

public final class LegitVelocity extends Mode<Velocity> {

    public LegitVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    private boolean jump;
    private boolean shouldjump;

    StopWatch watch = new StopWatch();

    Random random = new Random();


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        // disables before tick to prevent some checks
        jump = false;
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        // now lets look at this
        if(jump && watch.finished(random.nextInt(2))){
            event.setJump(true);
        }
    };

    @EventLink()
    public final Listener<TickEvent> onTick = Event -> {
        // this should only be a pass on
        if(shouldjump){
            jump = true;
            shouldjump = false;
        }
        else{
            jump = false;
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        // if swinging or sprinting then cancel
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress) return;

        // prevents fake velocity in the air
        if (!mc.thePlayer.onGround) {
            return;
        }

        // now look at when packet is received

        final Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                shouldjump = true;
                // if it is us, wait for 2 ticks then jump to make sure it does reset velocity
                watch.reset();
            }
        }

        if (packet instanceof S27PacketExplosion) {
            jump = true;
        }
    };
}
