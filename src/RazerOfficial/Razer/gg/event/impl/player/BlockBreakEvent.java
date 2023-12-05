package RazerOfficial.Razer.gg.event.impl.player;

import RazerOfficial.Razer.gg.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.BlockPos;

@Getter
@Setter
@AllArgsConstructor
public final class BlockBreakEvent extends CancellableEvent {

    private BlockPos blockPos;
}