package me.neilhuang007.razer.component.impl.viamcp;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import me.neilhuang007.razer.component.Component;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.MinimumMotionEvent;
import net.minecraft.viamcp.ViaMCP;

public final class MinimumMotionFixComponent extends Component {

    @EventLink()
    public final Listener<MinimumMotionEvent> onMinimumMotion = event -> {
        if (ViaMCP.getInstance().getVersion() > ProtocolVersion.v1_8.getVersion()) {
            event.setMinimumMotion(0.003D);
        }
    };
}
