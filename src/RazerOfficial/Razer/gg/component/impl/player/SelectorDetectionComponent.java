package RazerOfficial.Razer.gg.component.impl.player;

import RazerOfficial.Razer.gg.api.Rise;
import RazerOfficial.Razer.gg.component.Component;
import RazerOfficial.Razer.gg.event.Listener;
import RazerOfficial.Razer.gg.event.Priorities;
import RazerOfficial.Razer.gg.event.annotations.EventLink;
import RazerOfficial.Razer.gg.event.impl.motion.PreUpdateEvent;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

@Rise
public final class SelectorDetectionComponent extends Component  {

    private static boolean selector;

    public static boolean selector() {
        return selector;
    }

    public static boolean selector(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else if (itemStack == SlotComponent.getItemStack()) {
            return selector();
        } else {
            return !trueName(itemStack).contains(itemStack.getDisplayName());
        }
    }

    public static boolean selector(int itemSlot) {
        return selector(mc.thePlayer.inventory.getStackInSlot(itemSlot));
    }

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (SlotComponent.getItemStack() != null) {
            ItemStack itemStack = SlotComponent.getItemStack();

            selector = !trueName(itemStack).contains(itemStack.getDisplayName());
        } else {
            selector = false;
        }
    };

    public static String trueName(ItemStack itemStack) {
        String name = ("" + StatCollector.translateToLocal(itemStack.getUnlocalizedName() + ".name")).trim();
        final String s1 = EntityList.getStringFromID(itemStack.getMetadata());

        if (s1 != null) {
            name = name + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return name;
    }
}
