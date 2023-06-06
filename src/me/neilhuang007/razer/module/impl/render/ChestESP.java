package me.neilhuang007.razer.module.impl.render;

import me.neilhuang007.razer.api.Rise;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.render.Render3DEvent;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

import java.util.ConcurrentModificationException;

@Rise
@ModuleInfo(name = "module.render.chestesp.name", description = "module.render.chestesp.description", category = Category.RENDER)
public final class ChestESP extends Module {

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        final Runnable runnable = () -> {
            try {
                mc.theWorld.loadedTileEntityList.forEach(entity -> {
                    if (entity instanceof TileEntityChest || entity instanceof TileEntityEnderChest) {
                        RendererLivingEntity.setShaderBrightness(getTheme().getFirstColor());
                        TileEntityRendererDispatcher.instance.renderBasicTileEntity(entity, event.getPartialTicks());
                        RendererLivingEntity.unsetShaderBrightness();
                    }
                });
            } catch (final ConcurrentModificationException ignored) {
            }
        };

        NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
    };
}