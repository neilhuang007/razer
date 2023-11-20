package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.component.impl.player.PingSpoofComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.module.impl.player.Blink;
import RazerOfficial.Razer.gg.script.api.NetworkAPI;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import util.time.StopWatch;

import java.util.function.BooleanSupplier;

public final class StandardVelocity extends Mode<Velocity>{

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private final NumberValue chance = new NumberValue("chance", this, 0, 0, 100, 1);
    private final BooleanValue useblink = new BooleanValue("use Blink", this, true);

    private EntityOtherPlayerMP blinkEntity;

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
                if(isretoggle){
                    return;
                }else{
                    double x = wrapper.getMotionX() * hor, y = wrapper.getMotionY() * ver, z = wrapper.getMotionZ() * hor;
                    if (Math.abs(wrapper.getMotionX()) + Math.abs(wrapper.getMotionZ()) + Math.abs(wrapper.getMotionY()) < 3500) {
                        if(this.getParent().LagBackDetection.getValue()) {
                            if (Lagbacks >= this.getParent().LagBacks.getValue().intValue()) {
                                stopWatch.reset();
                                ChatUtil.display(ChatFormatting.RED + "Lagged Backed " + Lagbacks + " times, auto disabled to prevent " + ChatFormatting.DARK_PURPLE + "BAN");
                                Lagbacks = 0;
                                // start the retoggle timer
                                // the reason cannot do this here is because once unenabled all checks stopped working
                                if(this.getParent().retoggle.getValue()){
                                    ChatUtil.display(ChatFormatting.YELLOW + "retoggle delay started");
                                    isretoggle = true;
                                }else{
                                    ChatUtil.display(ChatFormatting.RED + "Module ShutDown excavated");
                                    Razer.INSTANCE.getModuleManager().get(Velocity.class).setEnabled(false);
                                }

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
                            if(useblink.getValue()){

                                Entity entity = mc.theWorld.getEntityByID(wrapper.getEntityID());
                                PingSpoofComponent.setSpoofing(999999999, true, true, false, true, true, true);
                                //despawn player first to ignore kb
                                deSpawnPlayer();
                                PingSpoofComponent.dispatch();

                                BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
                                PacketUtil.send(
                                        new C07PacketPlayerDigging(
                                                C07PacketPlayerDigging.Action.START_DESTROY_BLOCK,
                                                pos,
                                                EnumFacing.DOWN
                                        )
                                );

                                PingSpoofComponent.setSpoofing(999999999, true, true, false, true, true, true);
                                // send ground spoof packet
                                PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0F, mc.thePlayer.posZ,false));
                                // spawn a entity beneath u
                                spawnEntity();

                                PingSpoofComponent.dispatch();
                                entity.setVelocity((double) wrapper.getMotionX() / 8000.0D,
                                        (double) wrapper.getMotionY() / 8000.0D, (double) wrapper.getMotionZ() / 8000.0D);
//                                mc.thePlayer.motionX *= -1;
//                                mc.thePlayer.motionZ *= -1;
                                 //send mining packet
                                //BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1.0, mc.thePlayer.posZ);
                                PacketUtil.send(
                                        new C07PacketPlayerDigging(
                                                C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                                                pos,
                                                EnumFacing.DOWN
                                        )
                                );
//                                // apply the velocity on the fake one
                                event.setPacket(new S12PacketEntityVelocity(mc.thePlayer.getEntityId(), wrapper.motionX *= horizontal / 100, wrapper.motionY *= vertical / 100, wrapper.motionZ *= horizontal / 100));
                                deSpawnEntity();
                                // respawn player
                                //SpawnPlayer();

                            }else{
                                wrapper.motionX *= horizontal / 100;
                                wrapper.motionY *= vertical / 100;
                                wrapper.motionZ *= horizontal / 100;

                                event.setPacket(wrapper);
                            }

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

    public void deSpawnPlayer() {
        mc.theWorld.removeEntityFromWorld(mc.thePlayer.getEntityId());
    }

    public void SpawnPlayer() {
        mc.theWorld.addEntityToWorld(mc.thePlayer.getEntityId(),mc.thePlayer);
    }



    public void spawnEntity() {
        if (blinkEntity == null) {
            blinkEntity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            blinkEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY - 1.0F, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
            blinkEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
            blinkEntity.setSprinting(mc.thePlayer.isSprinting());
            blinkEntity.setInvisible(mc.thePlayer.isInvisible());
            blinkEntity.setSneaking(mc.thePlayer.isSneaking());
            blinkEntity.inventory = mc.thePlayer.inventory;
            blinkEntity.setInvisible(false);
            Razer.INSTANCE.getBotManager().add(blinkEntity);

            mc.theWorld.addEntityToWorld(blinkEntity.getEntityId(), blinkEntity);
        }
    }


    public StandardVelocity(String name, Velocity parent) {
        super(name, parent);
    }
}
