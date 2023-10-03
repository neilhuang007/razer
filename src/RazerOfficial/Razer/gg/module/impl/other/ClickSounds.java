package RazerOfficial.Razer.gg.module.impl.other;

import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.input.ClickEvent;
import RazerOfficial.Razer.gg.module.Module;
import RazerOfficial.Razer.gg.module.api.Category;
import RazerOfficial.Razer.gg.module.api.ModuleInfo;
import RazerOfficial.Razer.gg.util.sound.SoundUtil;
import RazerOfficial.Razer.gg.value.impl.ModeValue;
import RazerOfficial.Razer.gg.value.impl.NumberValue;
import RazerOfficial.Razer.gg.value.impl.SubMode;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(name = "module.other.clicksounds.name", description = "module.other.clicksounds.description", category = Category.OTHER)
public final class ClickSounds extends Module {

    private final ModeValue sound = new ModeValue("Sound", this)
            .add(new SubMode("Standard"))
            .add(new SubMode("Double"))
            .add(new SubMode("Alan"))
            .setDefault("Standard");

    private final NumberValue volume = new NumberValue("Volume", this, 0.5, 0.1, 2, 0.1);
    private final NumberValue variation = new NumberValue("Variation", this, 5, 0, 100, 1);

    @EventLink()
    public final Listener<ClickEvent> onClick = event -> {
        String soundName = "razor.click.standard";

        switch (sound.getValue().getName()) {
            case "Double": {
                soundName = "razor.click.double";
                break;
            }

            case "Alan": {
                soundName = "razor.click.alan";
                break;
            }
        }

        SoundUtil.playSound(soundName, volume.getValue().floatValue(), RandomUtils.nextFloat(1.0F, 1 + variation.getValue().floatValue() / 100f));
    };
}