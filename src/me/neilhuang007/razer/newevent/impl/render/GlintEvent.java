package me.neilhuang007.razer.newevent.impl.render;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.neilhuang007.razer.newevent.CancellableEvent;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;

@Getter
@Setter
@AllArgsConstructor
public final class GlintEvent extends CancellableEvent {

    private boolean enchanted, render;
    private ItemStack itemStack;
    private IBakedModel model;

}
