package RazerOfficial.Razer.gg.module.impl.render;


import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreMotionEvent;
import RazerOfficial.Razer.gg.event.impl.packet.PacketReceiveEvent;
import RazerOfficial.Razer.gg.event.impl.render.Render3DEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.interfaces.InstanceAccess;
import RazerOfficial.Razer.gg.value.impl.ColorValue;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import lombok.Getter;
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
        InstanceAccess.mc.theWorld.setRainStrength(0);
        InstanceAccess.mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        InstanceAccess.mc.theWorld.getWorldInfo().setRainTime(0);
        InstanceAccess.mc.theWorld.getWorldInfo().setThunderTime(0);
        InstanceAccess.mc.theWorld.getWorldInfo().setRaining(false);
        InstanceAccess.mc.theWorld.getWorldInfo().setThundering(false);
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        InstanceAccess.mc.theWorld.setWorldTime((time.getValue().intValue() + (System.currentTimeMillis() * speed.getValue().intValue())));
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.ticksExisted % 20 == 0) {

            switch (this.weather.getValue().getName()) {
                case "Clear": {
                    InstanceAccess.mc.theWorld.setRainStrength(0);
                    InstanceAccess.mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
                    InstanceAccess.mc.theWorld.getWorldInfo().setRainTime(0);
                    InstanceAccess.mc.theWorld.getWorldInfo().setThunderTime(0);
                    InstanceAccess.mc.theWorld.getWorldInfo().setRaining(false);
                    InstanceAccess.mc.theWorld.getWorldInfo().setThundering(false);
                    break;
                }
                case "Nether Particles":
                case "Light Snow":
                case "Heavy Snow":
                case "Rain": {
                    InstanceAccess.mc.theWorld.setRainStrength(1);
                    InstanceAccess.mc.theWorld.getWorldInfo().setCleanWeatherTime(0);
                    InstanceAccess.mc.theWorld.getWorldInfo().setRainTime(Integer.MAX_VALUE);
                    InstanceAccess.mc.theWorld.getWorldInfo().setThunderTime(Integer.MAX_VALUE);
                    InstanceAccess.mc.theWorld.getWorldInfo().setRaining(true);
                    InstanceAccess.mc.theWorld.getWorldInfo().setThundering(false);
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