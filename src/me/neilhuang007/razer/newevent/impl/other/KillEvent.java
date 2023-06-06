package me.neilhuang007.razer.newevent.impl.other;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.neilhuang007.razer.newevent.Event;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public final class KillEvent implements Event {

    Entity entity;

}