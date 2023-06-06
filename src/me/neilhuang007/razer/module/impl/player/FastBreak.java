package me.neilhuang007.razer.module.impl.player;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreUpdateEvent;
import me.neilhuang007.razer.util.player.PlayerUtil;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import me.neilhuang007.razer.value.impl.SubMode;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@Rise
@ModuleInfo(name = "module.player.fastbreak.name", description = "module.player.fastbreak.description", category = Category.PLAYER)
public final class FastBreak extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Percentage"))
            .add(new SubMode("Ticks"))
            .setDefault("Ticks");

    private final NumberValue speed = new NumberValue("Speed", this, 50, 0, 100, 1,  () -> mode.getValue().getName().equals("Ticks"));
    private final NumberValue ticks = new NumberValue("Ticks", this, 1, 1, 100, 1, () -> mode.getValue().getName().equals("Percentage"));

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        mc.playerController.blockHitDelay = 0;

        double percentageFaster = 0;

        switch (mode.getValue().getName()) {
            case "Percentage":
                percentageFaster = speed.getValue().doubleValue() / 100f;
                break;

            case "Ticks":
                if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    Block block = PlayerUtil.block(blockPos);

                    float blockHardness = block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos);
                    percentageFaster = blockHardness * ticks.getValue().intValue();
                }
                break;
        }

        if (mc.playerController.curBlockDamageMP > 1 - percentageFaster) {
            mc.playerController.curBlockDamageMP = 1;
        }
    };
}
