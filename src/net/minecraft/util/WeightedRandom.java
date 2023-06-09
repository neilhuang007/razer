package net.minecraft.util;

import java.util.Collection;
import java.util.Random;

public class WeightedRandom {
    /**
     * Returns the total weight of all items in a collection.
     *
     * @param collection Collection to get the total weight of
     */
    public static int getTotalWeight(final Collection<? extends Item> collection) {
        int i = 0;

        for (final Item weightedrandom$item : collection) {
            i += weightedrandom$item.itemWeight;
        }

        return i;
    }

    public static <T extends Item> T getRandomItem(final Random random, final Collection<T> collection, final int totalWeight) {
        if (totalWeight <= 0) {
            throw new IllegalArgumentException();
        } else {
            final int i = random.nextInt(totalWeight);
            return getRandomItem(collection, i);
        }
    }

    public static <T extends Item> T getRandomItem(final Collection<T> collection, int weight) {
        for (final T t : collection) {
            weight -= t.itemWeight;

            if (weight < 0) {
                return t;
            }
        }

        return null;
    }

    public static <T extends Item> T getRandomItem(final Random random, final Collection<T> collection) {
        return getRandomItem(random, collection, getTotalWeight(collection));
    }

    public static class Item {
        protected int itemWeight;

        public Item(final int itemWeightIn) {
            this.itemWeight = itemWeightIn;
        }
    }
}
