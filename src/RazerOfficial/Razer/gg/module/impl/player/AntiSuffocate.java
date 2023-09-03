package RazerOfficial.Razer.gg.module.impl.player;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.packet.PacketUtil;
import RazerOfficial.Razer.gg.value.impl.BooleanValue;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Rise
@ModuleInfo(name = "module.player.antisuffocate.name", description = "module.player.antifsuffocate.description", category = Category.PLAYER)
public class AntiSuffocate extends Module {

    private final BooleanValue swing = new BooleanValue("Swing", this, true);


    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.isEntityInsideOpaqueBlock()) {
            PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(mc.thePlayer).down(), EnumFacing.UP));

            if (swing.getValue()) {
                mc.thePlayer.swingItem();
            }
        }
    };
}