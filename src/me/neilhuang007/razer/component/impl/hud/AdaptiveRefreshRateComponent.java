package me.neilhuang007.razer.component.impl.hud;

import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.newevent.impl.render.Render2DEvent;
import me.neilhuang007.razer.ui.ingame.GuiIngameCache;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import util.time.StopWatch;


public class AdaptiveRefreshRateComponent extends Component {

    private boolean playerList, debugInfo;
    private boolean setDirty;
    private int disabledFor;
    private float health;
    public StopWatch stopWatch = new StopWatch();

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted % 100 == 0 || setDirty ||
                mc.gameSettings.keyBindPlayerList.isKeyDown() != playerList || mc.gameSettings.showDebugInfo != debugInfo ||
                mc.thePlayer.ticksExisted <= 10 || disabledFor > 0) {
            GuiIngameCache.dirty = true;
            playerList = mc.gameSettings.keyBindPlayerList.isKeyDown();
            debugInfo = mc.gameSettings.showDebugInfo;
            setDirty = false;
        }

        if (health != mc.thePlayer.getHealth()) {
            disabledFor = 3;
            health = mc.thePlayer.getHealth();
        }

        disabledFor--;
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.currentScreen != null) {
            setDirty = true;
            GuiIngameCache.dirty = true;
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Packet packet = event.getPacket();

        if (packet instanceof S02PacketChat || packet instanceof S30PacketWindowItems || packet instanceof S2FPacketSetSlot) {
            stopWatch.reset();
            setDirty = true;
        }

        if (packet instanceof S0DPacketCollectItem) {
            disabledFor = 5;
        }
    };
}