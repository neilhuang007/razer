package me.neilhuang007.razer.module.impl.other;

import me.neilhuang007.razer.module.Module;
import me.neilhuang007.razer.module.api.Category;
import me.neilhuang007.razer.module.api.ModuleInfo;
import me.neilhuang007.razer.newevent.Listener;
import me.neilhuang007.razer.newevent.annotations.EventLink;
import me.neilhuang007.razer.newevent.impl.input.ClickEvent;
import me.neilhuang007.razer.util.sound.SoundUtil;
import me.neilhuang007.razer.value.impl.ModeValue;
import me.neilhuang007.razer.value.impl.NumberValue;
import me.neilhuang007.razer.value.impl.SubMode;
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
        String soundName = "rise.click.standard";

        switch (sound.getValue().getName()) {
            case "Double": {
                soundName = "rise.click.double";
                break;
            }

            case "Alan": {
                soundName = "rise.click.alan";
                break;
            }
        }

        SoundUtil.playSound(soundName, volume.getValue().floatValue(), RandomUtils.nextFloat(1.0F, 1 + variation.getValue().floatValue() / 100f));
    };
}