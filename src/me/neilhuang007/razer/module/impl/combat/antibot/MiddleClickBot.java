package me.neilhuang007.razer.module.impl.combat.antibot;

import me.neilhuang007.razer.Client;
import me.neilhuang007.razer.bots.BotManager;
import me.neilhuang007.razer.module.impl.combat.AntiBot;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.value.Mode;
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
                BotManager botManager = Client.INSTANCE.getBotManager();
                Entity entity = mc.objectMouseOver.entityHit;

                if (botManager.contains(entity)) {
                    Client.INSTANCE.getBotManager().remove(entity);
                } else {
                    Client.INSTANCE.getBotManager().add(entity);
                }
            }
        } else down = false;
    };
}