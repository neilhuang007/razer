package RazerOfficial.Razer.gg.module.impl.movement.longjump;

import RazerOfficial.Razer.gg.component.impl.player.RotationComponent;
import RazerOfficial.Razer.gg.component.impl.player.SlotComponent;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.module.impl.movement.LongJump;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.MoveUtil;
import RazerOfficial.Razer.gg.util.player.SlotUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import RazerOfficial.Razer.gg.value.Mode;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class FireBallLongJump extends Mode<LongJump> {

    public ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Custom"))
            .add(new SubMode("Hypixel"))
            .setDefault("Hypixel");

    private NumberValue height = new NumberValue("Height", this, 1, 0.42, 9, 0.1, () -> !mode.getValue().getName().equals("Custom"));
    private NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 3, 0.1, () -> !mode.getValue().getName().equals("Custom"));
    private int tick;
    private double moveSpeed = 0;
    private float yawAtDamage;

    public FireBallLongJump(String name, LongJump parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        switch (mode.getValue().getName()) {
            case "Hypixel":
                if (mc.thePlayer.hurtTime == 10) {
                    yawAtDamage = mc.thePlayer.rotationYaw;
                    mc.thePlayer.motionY = 1.5;
                    moveSpeed = 1.4;
                }

                if (mc.thePlayer.hurtTime == 9) {
                    moveSpeed = 2 - Math.random() / 100f;
                }

                if (mc.thePlayer.ticksSinceVelocity <= 11) {
                    MoveUtil.strafe(moveSpeed, yawAtDamage);
                    moveSpeed -= (moveSpeed / 249.9) + Math.random() / 100f;
                }
                break;

            case "Custom":
                mc.thePlayer.motionY = height.getValue().doubleValue();
                MoveUtil.strafe(speed.getValue().doubleValue());
                break;
        }

        int item = SlotUtil.findItem(Items.fire_charge);

        if (mc.thePlayer.onGroundTicks == 1) {
            MoveUtil.stop();
        }

        if (item == -1) return;

        tick++;

        SlotComponent.setSlot(item);

        if (tick == 2) {
            PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
        }

        RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, 90), 10, MovementFix.OFF);
    };

    @Override
    public void onEnable() {
        tick = 0;
    }
}