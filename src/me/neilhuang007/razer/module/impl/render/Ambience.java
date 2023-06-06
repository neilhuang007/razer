package me.neilhuang007.razer.module.impl.render;


import lombok.Getter;
import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.motion.PreMotionEvent;
import me.neilhuang007.razer.newevent.impl.packet.PacketReceiveEvent;
import me.neilhuang007.razer.newevent.impl.render.Render3DEvent;
import me.neilhuang007.razer.value.impl.ColorValue;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import me.neilhuang007.razer.value.impl.SubMode;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.*;

@Getter
@ModuleInfo(name = "module.render.ambience.name", description = "module.render.ambience.description", category = Category.RENDER)
public final class Ambience extends Module {

    private final NumberValue time = new NumberValue("Time", this, 0, 0, 22999, 1);
    private final NumberValue speed = new NumberValue("Time Speed", this, 0, 0, 20, 1);

    private final ModeValue weather = new ModeValue("Weather", this) {{
        add(new SubMode("Unchanged"));
        add(new SubMode("Clear"));
        add(new SubMode("Rain"));
        add(new SubMode("Heavy Snow"));
        add(new SubMode("Light Snow"));
        add(new SubMode("Nether Particles"));
        setDefault("Unchanged");
    }};

    public final ColorValue snowColor = new ColorValue("Snow Color", this, Color.WHITE,
            () -> !weather.getValue().getName().equals("Heavy Snow") && !weather.getValue().getName().equals("Light Snow"));

    @Override
    protected void onDisable() {
        mc.theWorld.setRainStrength(0);
        mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        mc.theWorld.getWorldInfo().setRainTime(0);
        mc.theWorld.getWorldInfo().setThunderTime(0);
        mc.theWorld.getWorldInfo().setRaining(false);
        mc.theWorld.getWorldInfo().setThundering(false);
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        mc.theWorld.setWorldTime((time.getValue().intValue() + (System.currentTimeMillis() * speed.getValue().intValue())));
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.ticksExisted % 20 == 0) {

            switch (this.weather.getValue().getName()) {
                case "Clear": {
                    mc.theWorld.setRainStrength(0);
                    mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
                    mc.theWorld.getWorldInfo().setRainTime(0);
                    mc.theWorld.getWorldInfo().setThunderTime(0);
                    mc.theWorld.getWorldInfo().setRaining(false);
                    mc.theWorld.getWorldInfo().setThundering(false);
                    break;
                }
                case "Nether Particles":
                case "Light Snow":
                case "Heavy Snow":
                case "Rain": {
                    mc.theWorld.setRainStrength(1);
                    mc.theWorld.getWorldInfo().setCleanWeatherTime(0);
                    mc.theWorld.getWorldInfo().setRainTime(Integer.MAX_VALUE);
                    mc.theWorld.getWorldInfo().setThunderTime(Integer.MAX_VALUE);
                    mc.theWorld.getWorldInfo().setRaining(true);
                    mc.theWorld.getWorldInfo().setThundering(false);
                }
            }
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }

        else if (event.getPacket() instanceof S2BPacketChangeGameState && !this.weather.getValue().getName().equals("Unchanged")) {
            S2BPacketChangeGameState s2b = (S2BPacketChangeGameState) event.getPacket();

            if (s2b.getGameState() == 1 || s2b.getGameState() == 2) {
                event.setCancelled(true);
            }
        }
    };

    public float getFloatTemperature(BlockPos blockPos, BiomeGenBase biomeGenBase) {
        if (this.isEnabled()) {
            switch (this.weather.getValue().getName()) {
                case "Nether Particles":
                case "Light Snow":
                case "Heavy Snow": return 0.1F;
                case "Rain": return 0.2F;
            }
        }

        return biomeGenBase.getFloatTemperature(blockPos);
    }

    public boolean skipRainParticles() {
        final String name = this.weather.getValue().getName();
        return this.isEnabled() && name.equals("Light Snow") || name.equals("Heavy Snow") || name.equals("Nether Particles");
    }
}