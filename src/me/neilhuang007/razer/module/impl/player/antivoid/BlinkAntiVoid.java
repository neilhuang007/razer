package me.neilhuang007.razer.module.impl.player.antivoid;

import me.neilhuang007.razer.component.impl.player.BlinkComponent;
import me.neilhuang007.razer.component.impl.player.FallDistanceComponent;
import me.neilhuang007.razer.module.impl.movement.LongJump;
import me.neilhuang007.razer.module.impl.other.Test;
import me.neilhuang007.razer.module.impl.player.AntiVoid;
import me.neilhuang007.razer.module.impl.player.Scaffold;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.util.packet.PacketUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
import me.neilhuang007.razer.value.impl.BooleanValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.Vec3;

public class BlinkAntiVoid extends Mode<AntiVoid> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);
    private final BooleanValue toggleScaffold = new BooleanValue("Toggle Scaffold", this, false);
    private Vec3 position, motion;
    private boolean wasVoid, setBack;
    private int overVoidTicks;
    private Scaffold scaffold;
    private LongJump longJump;
    private Test test;

    public BlinkAntiVoid(String name, AntiVoid parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        BlinkComponent.blinking = false;
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (mc.thePlayer.ticksExisted <= 50) return;

        if (scaffold == null) {
            scaffold = getModule(Scaffold.class);
        }

        if (longJump == null) {
            longJump = getModule(LongJump.class);
        }

        if (test == null) {
            test = getModule(Test.class);
        }

        if (scaffold.isEnabled() || longJump.isEnabled() || test.isEnabled()) {
            return;
        }

        boolean overVoid = !mc.thePlayer.onGround && !PlayerUtil.isBlockUnder(30, true);

        if (overVoid) {
            overVoidTicks++;
        } else if (mc.thePlayer.onGround) {
            overVoidTicks = 0;
        }

        if (overVoid && position != null && motion != null && overVoidTicks < 30 + distance.getValue().doubleValue() * 20) {
            if (!setBack) {
                wasVoid = true;

                BlinkComponent.blinking = true;
                BlinkComponent.setExempt(C0FPacketConfirmTransaction.class, C00PacketKeepAlive.class, C01PacketChatMessage.class);

                if (FallDistanceComponent.distance > distance.getValue().doubleValue() || setBack) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(position.xCoord, position.yCoord - 0.1 - Math.random(), position.zCoord, false));
                    if (this.toggleScaffold.getValue()) {
                        getModule(Scaffold.class).setEnabled(true);
                    }

                    BlinkComponent.packets.clear();

                    FallDistanceComponent.distance = 0;

                    setBack = true;
                }
            } else {
                BlinkComponent.blinking = false;
            }
        } else {

            setBack = false;

            if (wasVoid) {
                BlinkComponent.blinking = false;
                wasVoid = false;
            }

            motion = new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        }
    };
}