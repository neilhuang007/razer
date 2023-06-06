package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.SubMode;
import net.minecraft.client.entity.EntityOtherPlayerMP;

@Rise
@ModuleInfo(name = "module.other.cheatdetector.name", description = "module.other.cheatdetector.description", category = Category.OTHER)
public final class CheatDetector extends Module {

    public ModeValue alertType = new ModeValue("Alert Type", this)
            .add(new SubMode("ClientSide"))
            .add(new SubMode("ServerSide"))
            .setDefault("ClientSide");

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        Client.INSTANCE.getCheatDetector().incrementTick();
    };

    @Override
    protected void onDisable() {
        Client.INSTANCE.getCheatDetector().playerMap.clear();
    }

    @Override
    protected void onEnable() {
        Client.INSTANCE.getCheatDetector().playerMap.clear();
        mc.theWorld.playerEntities.forEach(entityPlayer -> {
            if (entityPlayer != mc.thePlayer) {
                Client.INSTANCE.getCheatDetector().getRegistrationListener().handleSpawn((EntityOtherPlayerMP) entityPlayer);
            }
        });
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Client.INSTANCE.getCheatDetector().playerMap.values().forEach(playerData -> playerData.handle(event.getPacket()));
    };
}
