package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.player.PingSpoofComponent;
import RazerOfficial.Razer.gg.component.impl.render.NotificationComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.impl.combat.KillAura;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.notifications.windows.NotificationUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.*;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.optifine.config.RangeInt;
import util.time.StopWatch;

import java.util.Random;

public class GrimVelocity extends Mode<Velocity> {



    public GrimVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    private Boolean isretoggle = false;

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private final NumberValue chance = new NumberValue("Chance", this, 100, 0, 100, 1);

    StopWatch stopWatch = new StopWatch();
    private Integer Lagbacks = 0;

    private Boolean shouldcancel;

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        // retoggle timer
        if(isretoggle && stopWatch.finished(this.getParent().RetoggleDelay.getValue().longValue() * 1000)){
            NotificationComponent.post("Velocity Retoggled","Velocity Retoggled after " + this.getParent().RetoggleDelay.getValue() + "seconds");
            stopWatch.reset();
            isretoggle = false;
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() || getParent().onSprint.getValue() && !mc.thePlayer.isSwingInProgress)
            return;
        final Packet<?> p = event.getPacket();

        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();

        if (p instanceof C0FPacketConfirmTransaction){
            if(shouldcancel){
                event.setCancelled(true);
                shouldcancel = false;
            }
        }

        if (p instanceof S12PacketEntityVelocity) {

            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;
            // detection of lagback should be here
            float hor = (float) (horizontal / 100);
            float ver = (float) (vertical / 100);

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                if (isretoggle) {
                    return;
                } else {
                    double x = wrapper.getMotionX() * hor, y = wrapper.getMotionY() * ver, z = wrapper.getMotionZ() * hor;
                    if (Math.abs(wrapper.getMotionX()) + Math.abs(wrapper.getMotionZ()) + Math.abs(wrapper.getMotionY()) < 3500) {
                        if (this.getParent().LagBackDetection.getValue()) {
                            if (Lagbacks >= this.getParent().LagBacks.getValue().intValue()) {
                                stopWatch.reset();
                                NotificationComponent.post("LagBacks Detected","Lagged Backed " + Lagbacks + " times, auto disabled to prevent " + ChatFormatting.DARK_PURPLE + "BAN");
                                Lagbacks = 0;
                                // start the retoggle timer
                                // the reason cannot do this here is because once unenabled all checks stopped working
                                if (this.getParent().retoggle.getValue()) {
                                    NotificationComponent.post("Retoggle Timer Started", "retoggle delay started");
                                    isretoggle = true;
                                } else {
                                    NotificationComponent.post("Velocity Disabled","Module ShutDown excavated");
                                    Razer.INSTANCE.getModuleManager().get(Velocity.class).setEnabled(false);
                                }

                            } else {
                                Lagbacks += 1;
                                NotificationComponent.post("LagBacks Detected","Fixed LagBack, You may be detected" + "S12 #" + mc.thePlayer.ticksExisted + " this is the " + Lagbacks + " time");
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        // if in chance
                        if(Math.random() <= chance.getValue().doubleValue()){

                            // no fake velocity

                            if(!mc.thePlayer.onGround){
                                return;
                            }
                            shouldcancel = true;
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    };
}
