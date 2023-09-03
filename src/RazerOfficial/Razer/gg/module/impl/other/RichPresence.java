package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.Razer;
import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.other.TickEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@Rise
@ModuleInfo(name = "module.other.richpresence.name", description = "module.other.richpresence.description", category = Category.OTHER, autoEnabled = true)
public final class RichPresence extends Module {

    private boolean started;

    @EventLink()
    public final Listener<TickEvent> onTick = event -> {

        if (!started) {
            final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder("") {{
                setDetails("Rise " + Razer.VERSION_FULL);
                setBigImage("rise", "");
                setStartTimestamps(System.currentTimeMillis());
            }};

            DiscordRPC.discordUpdatePresence(builder.build());

            final DiscordEventHandlers handlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize("1030003698685968485", handlers, true);

            new Thread(() -> {
                while (this.isEnabled()) {
                    DiscordRPC.discordRunCallbacks();
                }
            }, "Discord RPC Callback").start();

            started = true;
        }
    };

    @Override
    protected void onDisable() {
        DiscordRPC.discordShutdown();
        started = false;
    }
}
