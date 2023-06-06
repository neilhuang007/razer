package me.neilhuang007.razer.newevent.impl.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import net.minecraft.entity.Entity;

@Getter
@Setter
@AllArgsConstructor
public final class AttackEvent extends CancellableEvent {
    private Entity target;
}