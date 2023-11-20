package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.chat.ChatUtil;
import RazerOfficial.Razer.gg.util.player.ServerUtil;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import RazerOfficial.Razer.gg.value.impl.StringValue;
import util.time.StopWatch;

@Razer
@ModuleInfo(name = "module.other.spammer.name", description = "module.other.spammer.description", category = Category.OTHER)
public final class  Spammer extends Module {

    private final StringValue message = new StringValue("Message", this, "Buy Razer at riseclient.com!");
    private final NumberValue delay = new NumberValue("Delay", this, 3000, 0, 20000, 1);

    private final StopWatch stopWatch = new StopWatch();

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (ServerUtil.isOnServer("loyisa.cn")) {
            ChatUtil.display("Upon a request from Loyisa we have blacklisted Loyisa's Test Server from Spammer.");
            this.toggle();
            return;
        }

        if (this.stopWatch.finished(delay.getValue().longValue())) {
            mc.thePlayer.sendChatMessage(message.getValue());
            this.stopWatch.reset();
        }
    };
}
