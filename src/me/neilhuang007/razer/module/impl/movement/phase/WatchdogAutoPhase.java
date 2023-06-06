package me.neilhuang007.razer.module.impl.movement.phase;

import me.neilhuang007.razer.component.impl.player.BlinkComponent;
import me.neilhuang007.razer.module.impl.movement.Phase;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.BlockAABBEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S02PacketChat;

public class WatchdogAutoPhase extends Mode<Phase> {

    private int phaseTime;
    private boolean phase;

    public WatchdogAutoPhase(String name, Phase parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        phase = false;
        phaseTime = 0;
    }

    @Override
    public void onDisable() {
        BlinkComponent.blinking = false;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (this.phase) {
            this.phaseTime++;
        } else {
            this.phaseTime = 0;
        }

        if (this.phase) {
            BlinkComponent.setExempt(C0FPacketConfirmTransaction.class, C00PacketKeepAlive.class);
            BlinkComponent.blinking = true;
        }
    };

    @EventLink()
    public final Listener<BlockAABBEvent> onBlockAABB = event -> {
        if (this.phase && this.phaseTime <= 5) {
            event.setBoundingBox(null);
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        this.phase = false;
        this.phaseTime = 0;
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = ((S02PacketChat) packet);
            String chat = s02PacketChat.getChatComponent().getUnformattedText();

            if (chat.contains(" 2 ") && chat.contains("game")) {
                this.phase = true;
            } else if (chat.contains("FIGHT") && chat.contains("Cages")) {
                this.phase = false;
                BlinkComponent.blinking = false;
            }
        }
    };
}