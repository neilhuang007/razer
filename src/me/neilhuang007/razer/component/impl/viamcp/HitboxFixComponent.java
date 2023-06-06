package me.neilhuang007.razer.component.impl.viamcp;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.MouseOverEvent;
import net.minecraft.viamcp.ViaMCP;

public final class HitboxFixComponent extends Component {

    @EventLink()
    public final Listener<MouseOverEvent> onMouseOver = event -> {

        if (ViaMCP.getInstance().getVersion() > ProtocolVersion.v1_8.getVersion()) {
            event.setExpand(event.getExpand() - 0.1f);
        }
    };
}
