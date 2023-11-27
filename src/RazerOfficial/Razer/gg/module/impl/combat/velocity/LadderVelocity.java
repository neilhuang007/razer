package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.player.PingSpoofComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import util.time.StopWatch;

public class LadderVelocity extends Mode<Velocity> {

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private Boolean isretoggle = false;



    StopWatch stopWatch = new StopWatch();
    private Integer Lagbacks = 0;
    @EventLink
    public final Listener<TickEvent> onTick = event -> {

        // retoggle timer
        if(isretoggle && stopWatch.finished(this.getParent().RetoggleDelay.getValue().longValue() * 1000)){
            ChatUtil.display(ChatFormatting.GREEN + "Velocity Retoggled");
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
                                ChatUtil.display(ChatFormatting.RED + "Lagged Backed " + Lagbacks + " times, auto disabled to prevent " + ChatFormatting.DARK_PURPLE + "BAN");
                                Lagbacks = 0;
                                // start the retoggle timer
                                // the reason cannot do this here is because once unenabled all checks stopped working
                                if (this.getParent().retoggle.getValue()) {
                                    ChatUtil.display(ChatFormatting.YELLOW + "retoggle delay started");
                                    isretoggle = true;
                                } else {
                                    ChatUtil.display(ChatFormatting.RED + "Module ShutDown excavated");
                                    Razer.INSTANCE.getModuleManager().get(Velocity.class).setEnabled(false);
                                }

                            } else {
                                Lagbacks += 1;
                                ChatUtil.display(ChatFormatting.YELLOW + "Fixed LagBack, You may be detected" + "S12 #" + mc.thePlayer.ticksExisted + " this is the " + Lagbacks + " time");
                                event.setCancelled(true);
                            }
                        }
                    } else {

                        // no fake velocity

                        if(!mc.thePlayer.onGround){
                            return;
                        }

                        // spawn ladder packet below the player
                        // by lagging into the ground and set spider velocity
                        PingSpoofComponent.setSpoofing(999999999, true, true, false, true, true, true);
                        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.000000012F, mc.thePlayer.posZ,true));
                        PacketUtil.sendNoEvent(new S12PacketEntityVelocity(mc.thePlayer.getEntityId(),wrapper.motionX, 0.002, wrapper.motionY));
                        PingSpoofComponent.dispatch();
                        wrapper.motionX *= horizontal / 100;
                        wrapper.motionY *= vertical / 100;
                        wrapper.motionZ *= horizontal / 100;

                        event.setPacket(wrapper);

                        event.setPacket(wrapper);

                    }
                }
            }
        }
    };

    public LadderVelocity(String name, Velocity parent) {
        super(name, parent);
    }
}
