package RazerOfficial.Razer.gg.component.impl.player;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.component.impl.player.rotationcomponent.MovementFix;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.MoveInputEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.util.math.MathUtil;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.util.player.SlotUtil;
import RazerOfficial.Razer.gg.util.vector.Vector2f;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Razer
public final class ItemDamageComponent extends Component {

    public static boolean active;
    private static boolean stop;
    private static int slot, time;
    public static Type type;


    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (active) {
            final int bowSlot = SlotUtil.findItem(Items.bow);
            final int rodSlot = SlotUtil.findItem(Items.fishing_rod);
            final int snowBall = SlotUtil.findItem(Items.snowball);
            final int egg = SlotUtil.findItem(Items.egg);

            if (bowSlot != -1 && arrow()) {
                SlotComponent.setSlot(bowSlot);
                type = Type.BOW;
            } else if (rodSlot != -1) {
                SlotComponent.setSlot(rodSlot);
                type = Type.ROD;
            } else if (snowBall != -1) {
                SlotComponent.setSlot(snowBall);
                type = Type.PROJECTILES;
            } else if (egg != -1) {
                SlotComponent.setSlot(egg);
                type = Type.PROJECTILES;
            }

            if (BadPacketsComponent.bad(true, false, false, false, true)) {
                time = 0;
            }

            if (!BadPacketsComponent.bad(true, false, false, false, true)) {
                time++;

                if (type != null) {
                    switch (type) {
                        case BOW:
                            switch (time) {
                                case 3:
                                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                                    break;

                                case 7:
                                    PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                                    break;

                                case 40:
                                    active = false;
                                    break;
                            }
                            break;

                        case ROD:
                            switch (time) {
                                case 3:
                                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                                    break;

                                case 95:
                                    active = false;
                                    break;
                            }

                            if (mc.thePlayer.hurtTime == 9) {
                                PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                            }
                            break;

                        case PROJECTILES:
                            switch (time) {
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                    PacketUtil.send(new C08PacketPlayerBlockPlacement(SlotComponent.getItemStack()));
                                    break;

                                case 100:
                                    active = false;
                                    break;
                            }
                            break;

                        default:
                            active = false;
                            break;
                    }
                } else {
                    active = false;
                }

                if (mc.thePlayer.hurtTime == 9) {
                    active = false;
                }

                stop = true;
            }
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (stop && active) {
            event.setForward(0);
            event.setStrafe(0);
        }
    };

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (active) {
            RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw, -90), MathUtil.getRandom(8, 10), MovementFix.NORMAL);
        }
    };

    public static void damage(final boolean stop) {
        active = true;
        ItemDamageComponent.stop = stop;
        slot = mc.thePlayer.inventory.currentItem;
        time = 0;
        type = null;
    }

    public boolean arrow() {
        for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
            final ItemStack stack = mc.thePlayer.inventory.mainInventory[i];

            if (stack != null && stack.getItem().getUnlocalizedName().contains("arrow")) {
                return true;
            }
        }

        return false;
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                active = false;
            }
        } else if (packet instanceof S27PacketExplosion) {
            active = false;
        }
    };

    public enum Type {
        BOW,
        ROD,
        PROJECTILES
    }
}
