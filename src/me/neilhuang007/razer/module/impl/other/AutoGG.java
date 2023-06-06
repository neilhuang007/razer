package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.other.WorldChangeEvent;
import me.neilhuang007.razer.value.impl.StringValue;

@Rise
@ModuleInfo(name = "module.other.autogg.name", description = "module.other.autogg.description", category = Category.OTHER)
public final class AutoGG extends Module {
    private StringValue message = new StringValue("Message", this, "Why waste another game without Rise?");
    private boolean active;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted % 18 != 0 || mc.thePlayer.ticksExisted < 20 * 20 || !active || !mc.thePlayer.sendQueue.doneLoadingTerrain) return;

        if (mc.theWorld.playerEntities.stream().filter(entityPlayer -> !entityPlayer.isInvisible()).count() <= 1) {
            active = false;

            mc.thePlayer.sendChatMessage(message.getValue());
        }
    };

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        active = true;
    };
}
