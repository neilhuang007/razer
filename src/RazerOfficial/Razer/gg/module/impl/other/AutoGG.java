package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.other.WorldChangeEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.value.impl.StringValue;

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
