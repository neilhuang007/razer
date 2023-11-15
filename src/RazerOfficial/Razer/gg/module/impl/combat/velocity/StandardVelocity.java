package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.player.PingSpoofComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.module.impl.player.Blink;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import util.time.StopWatch;

import java.util.function.BooleanSupplier;

public final class StandardVelocity extends Mode<Velocity> {

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private final NumberValue chance = new NumberValue("chance", this, 0, 0, 100, 1);
    private final BooleanValue useblink = new BooleanValue("use Blink", this, true);

    public final BooleanValue LagBackDetection = new BooleanValue("Lagback Detections", this, true);



    public final NumberValue LagBacks = new NumberValue("Lagbacks Disable", this, 0, 0, 50, 1, () -> !LagBackDetection.getValue());

    public final BooleanValue retoggle = new BooleanValue("retoggle", this, true,() -> !LagBackDetection.getValue());

    public final NumberValue RetoggleDelay = new NumberValue("Retoggle Delay(ms)", this, 0.5F,0.1,3,0.1,()-> !LagBackDetection.getValue());


    private EntityOtherPlayerMP blinkEntity;

    private Boolean isretoggle = false;





    StopWatch stopWatch = new StopWatch();
    private Integer Lagbacks = 0;

    @EventLink
    public final Listener<TickEvent> onTick = event -> {

        // retoggle timer
        if(isretoggle && stopWatch.finished(RetoggleDelay.getValue().longValue() * 1000)){
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
                if(isretoggle){
                    return;
                }else{
                    double x = wrapper.getMotionX() * hor, y = wrapper.getMotionY() * ver, z = wrapper.getMotionZ() * hor;
                    if (Math.abs(wrapper.getMotionX()) + Math.abs(wrapper.getMotionZ()) + Math.abs(wrapper.getMotionY()) < 3500) {
                        if(LagBackDetection.getValue()) {
                            if (Lagbacks >= LagBacks.getValue().intValue()) {
                                stopWatch.reset();
                                ChatUtil.display(ChatFormatting.RED + "Lagged Backed " + Lagbacks + " times, auto disabled to prevent " + ChatFormatting.DARK_PURPLE + "BAN");
                                Lagbacks = 0;
                                // start the retoggle timer
                                // the reason cannot do this here is because once unenabled all checks stopped working
                                isretoggle = true;
                            }else{
                                Lagbacks += 1;
                                ChatUtil.display(ChatFormatting.YELLOW + "Fixed LagBack, You may be detected" + "S12 #" + mc.thePlayer.ticksExisted + " this is the " + Lagbacks + " time");
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        // if in chance
                        if (Math.random() <= chance.getValue().intValue() / 100) {
                            // prevents fake velocity in the air
                            if (!mc.thePlayer.onGround) {
                                return;
                            }

                            // cancel to apply effects to this action
                            event.setCancelled(true);
                            // packet burst to prevent stimulation checks
                            PingSpoofComponent.setSpoofing(999999999, true, true, false, true, true, true);
                            // give the velocity
                            event.setPacket(new S12PacketEntityVelocity(mc.thePlayer.getEntityId(), wrapper.motionX *= horizontal / 100, wrapper.motionY *= vertical / 100, wrapper.motionZ *= horizontal / 100));
                            PingSpoofComponent.dispatch();
                        } else {
                            // just don do anythin
                            wrapper.motionX = wrapper.getMotionX();
                            wrapper.motionY = wrapper.getMotionY();
                            wrapper.motionZ = wrapper.getMotionZ();
                            event.setPacket(wrapper);
                        }
                    }
                }
            }
        } else if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;
            if (getParent().onExplosion.getValue()) {
                if (horizontal == 0 && vertical == 0) {
                    event.setCancelled(true);
                    return;
                }

                wrapper.posX *= horizontal / 100;
                wrapper.posY *= vertical / 100;
                wrapper.posZ *= horizontal / 100;

                event.setPacket(wrapper);
            } else {
                event.setPacket(wrapper);
            }
        }
    };


    public void deSpawnEntity() {
        if (blinkEntity != null) {
            Razer.INSTANCE.getBotManager().remove(blinkEntity);
            mc.theWorld.removeEntityFromWorld(blinkEntity.getEntityId());
            blinkEntity = null;
        }
    }

    public void spawnEntity() {
        if (blinkEntity == null) {
            blinkEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
            blinkEntity.setSprinting(mc.thePlayer.isSprinting());
            blinkEntity.setInvisible(mc.thePlayer.isInvisible());
            blinkEntity.setSneaking(mc.thePlayer.isSneaking());
            blinkEntity.inventory = mc.thePlayer.inventory;
            Razer.INSTANCE.getBotManager().add(blinkEntity);

            mc.theWorld.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        }
    }


    public StandardVelocity(String name, Velocity parent) {
        super(name, parent);
    }
}
