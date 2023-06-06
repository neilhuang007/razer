package me.neilhuang007.razer.newevent.impl.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import net.minecraft.util.BlockPos;

@Getter
@Setter
@AllArgsConstructor
public final class BlockBreakEvent extends CancellableEvent {

    private BlockPos blockPos;
}