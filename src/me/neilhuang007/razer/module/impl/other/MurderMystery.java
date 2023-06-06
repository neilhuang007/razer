package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import me.neilhuang007.razer.util.chat.ChatUtil;
import me.neilhuang007.razer.util.player.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;

@Rise
@ModuleInfo(name = "module.other.murdermystery.name", description = "module.other.murdermystery.description", category = Category.OTHER)
public final class MurderMystery extends Module {

    private EntityPlayer murderer;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        // no need to waste performance so every second tick is enough
        if (mc.thePlayer.ticksExisted % 2 == 0 || this.murderer != null) {
            return;
        }

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player.getHeldItem() != null) {
                if (player.getHeldItem().getDisplayName().contains("Knife")) { // TODO: add other languages
                    ChatUtil.display(PlayerUtil.name(player) + " is The Murderer.");
                    this.murderer = player;
                }
            }
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> this.murderer = null;
}
