package me.neilhuang007.razer.newevent.impl.render;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

@Getter
@Setter
@AllArgsConstructor
public final class RenderItemEvent extends CancellableEvent {

    private EnumAction enumAction;
    private boolean useItem;
    private float animationProgression, partialTicks, swingProgress;
    private ItemStack itemToRender;

}
