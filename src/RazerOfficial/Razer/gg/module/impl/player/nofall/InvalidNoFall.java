package RazerOfficial.Razer.gg.module.impl.player.nofall;

import RazerOfficial.Razer.gg.component.impl.player.FallDistanceComponent;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.player.NoFall;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
public class InvalidNoFall extends Mode<NoFall> {

    public InvalidNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.motionY > 0) {
            return;
        }

        float distance = FallDistanceComponent.distance;

        if (distance > 3) {
            final Block nextBlock = PlayerUtil.block(new BlockPos(event.getPosX(), event.getPosY() + mc.thePlayer.motionY, event.getPosZ()));

            if (nextBlock.getMaterial().isSolid()) {
                event.setPosY(event.getPosY() - 999);
                distance = 0;
            }
        }

        FallDistanceComponent.distance = distance;
    };
}