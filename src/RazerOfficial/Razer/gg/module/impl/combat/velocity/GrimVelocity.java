package RazerOfficial.Razer.gg.module.impl.combat.velocity;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.impl.combat.KillAura;
import RazerOfficial.Razer.gg.module.impl.combat.Velocity;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import RazerOfficial.Razer.gg.value.impl.ListValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;

public class GrimVelocity extends Mode<Velocity> {

    private ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Silky"))
            .add(new SubMode("Grim Combat"))
            .add(new SubMode("Grim Safe"))
            .add(new SubMode("Jump Reset"))
            .add(new SubMode("None"))
            .setDefault("Grim Safe");

    private final BooleanValue swingFix = new BooleanValue("1.9+ Swing Fix", this,false, () -> mode.getValue().equals("Grim Combat"));




    private int start = 0;

    public GrimVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity) packet).entityID != mc.thePlayer.getEntityId()) {
                return;
            }
//            System.out.println("VELO EVENT");
//            System.out.println(mode.getValue().getName());
            if (mode.getValue().getName().contains("Silky")) {
                //System.out.println("Silky");
                event.setCancelled(true);
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = ((S12PacketEntityVelocity) packet).getMotionY() / 8000.0;
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed) && MoveUtil.isMoving()) MoveUtil.strafe();
                }
            } else if (mode.getValue().getName().contains("Grim Combat")) {
                //System.out.println("Grim Combat");
                final KillAura killAura = ((KillAura) (Razer.INSTANCE.getModuleManager().get(KillAura.class)));
                final Entity target = killAura.target != null ? killAura.target : null;

                if (target == null) return;

                for (int i = 0; i < 12; ++i) {
                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction());

                    if (!swingFix.getValue()) mc.getNetHandler().addToSendQueue(new C0APacketAnimation());

                    mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                    if (swingFix.getValue()) mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                }
            } else if (mode.getValue().getName().contains("Grim Safe")) {
                System.out.println("Grim Safe");
                if (mc.thePlayer.hurtTime > 0) {
                    mc.thePlayer.motionX += -1.0E-7;
                    mc.thePlayer.motionY += -1.0E-7;
                    mc.thePlayer.motionZ += -1.0E-7;
                    mc.thePlayer.isAirBorne = true;
                }
            } else if (mode.getValue().getName().contains("Jump Reset")) {
                if (mc.thePlayer.hurtTime >= 8) {
                    mc.gameSettings.keyBindJump.setPressed(true);
                }

                if (mc.thePlayer.hurtTime >= 7 && !mc.gameSettings.keyBindForward.isPressed()) {
                    mc.gameSettings.keyBindForward.setPressed(true);
                    start = 1;
                }
                if (mc.thePlayer.hurtTime >= 1 && mc.thePlayer.hurtTime <= 6) {
                    mc.gameSettings.keyBindJump.setPressed(false);
                    if (start == 1) {
                        mc.gameSettings.keyBindForward.setPressed(false);
                        start = 0;
                    }
                }
            } else if (mode.getValue().getName().contains("None")) {
            }
        }
    };
}
