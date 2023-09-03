package RazerOfficial.Razer.gg.module.impl.combat.antibot;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.bots.BotManager;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.impl.combat.AntiBot;
import RazerOfficial.Razer.gg.value.Mode;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public final class MiddleClickBot extends Mode<AntiBot> {

    private boolean down;

    public MiddleClickBot(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (Mouse.isButtonDown(2)) {
            if (down) return;
            down = true;

            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                BotManager botManager = Razer.INSTANCE.getBotManager();
                Entity entity = mc.objectMouseOver.entityHit;

                if (botManager.contains(entity)) {
                    Razer.INSTANCE.getBotManager().remove(entity);
                } else {
                    Razer.INSTANCE.getBotManager().add(entity);
                }
            }
        } else down = false;
    };
}