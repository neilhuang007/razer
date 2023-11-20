package RazerOfficial.Razer.gg.module.impl.player;


import RazerOfficial.Razer.gg.api.Razer;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.module.impl.player.antivoid.*;
import RazerOfficial.Razer.gg.value.impl.ModeValue;


/**
 * @author Alan
 * @since 23/10/2021
 */

@Razer
@ModuleInfo(name = "module.player.antivoid.name", description = "module.player.antivoid.description", category = Category.PLAYER)
public class AntiVoid extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketAntiVoid("Packet", this))
            .add(new PositionAntiVoid("Position", this))
            .add(new BlinkAntiVoid("Blink", this))
            .add(new BlinkAntiVoid("Watchdog", this))
            .add(new VulcanAntiVoid("Vulcan", this))
            .add(new CollisionAntiVoid("Collision", this))
            .setDefault("Packet");
}