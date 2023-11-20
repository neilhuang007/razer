package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.player.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;

@Razer
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
