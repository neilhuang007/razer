package me.neilhuang007.razer.module.impl.player.nofall;

import me.neilhuang007.razer.component.impl.player.FallDistanceComponent;
import me.neilhuang007.razer.module.impl.player.NoFall;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.Mode;
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