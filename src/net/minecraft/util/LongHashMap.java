package net.minecraft.util;

public class LongHashMap<V> {
    private transient Entry<V>[] hashArray = new Entry[4096];

    /**
     * the number of elements in the hash array
     */
    private transient int numHashElements;
    private int mask;

    /**
     * the maximum amount of elements in the hash (probably 3/4 the size due to meh hashing function)
     */
    private int capacity = 3072;

    /**
     * percent of the hasharray that can be used without hash colliding probably
     */
    private final float percentUseable = 0.75F;

    /**
     * count of times elements have been added/removed
     */
    private transient volatile int modCount;

    public LongHashMap() {
        this.mask = this.hashArray.length - 1;
    }

    /**
     * returns the hashed key given the original key
     */
    private static int getHashedKey(final long originalKey) {
        return (int) (originalKey ^ originalKey >>> 27);
    }

    /**
     * the hash function
     *
     * @param integer Integer to make suitable for hashing
     */
    private static int hash(int integer) {
        integer = integer ^ integer >>> 20 ^ integer >>> 12;
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }

    /**
     * gets the index in the hash given the array length and the hashed key
     */
    private static int getHashIndex(final int p_76158_0_, final int p_76158_1_) {
        return p_76158_0_ & p_76158_1_;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    /**
     * get the value from the map given the key
     */
    public V getValueByKey(final long p_76164_1_) {
        final int i = getHashedKey(p_76164_1_);

        for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76164_1_) {
                return entry.value;
            }
        }

        return null;
    }

    public boolean containsItem(final long p_76161_1_) {
        return this.getEntry(p_76161_1_) != null;
    }

    final Entry<V> getEntry(final long p_76160_1_) {
        final int i = getHashedKey(p_76160_1_);

        for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76160_1_) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Add a key-value pair.
     */
    public void add(final long p_76163_1_, final V p_76163_3_) {
        final int i = getHashedKey(p_76163_1_);
        final int j = getHashIndex(i, this.mask);

        for (Entry<V> entry = this.hashArray[j]; entry != null; entry = entry.nextEntry) {
            if (entry.key == p_76163_1_) {
                entry.value = p_76163_3_;
                return;
            }
        }

        ++this.modCount;
        this.createKey(i, p_76163_1_, p_76163_3_, j);
    }

    /**
     * resizes the table
     */
    private void resizeTable(final int p_76153_1_) {
        final Entry<V>[] entry = this.hashArray;
        final int i = entry.length;

        if (i == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
        } else {
            final Entry<V>[] entry1 = new Entry[p_76153_1_];
            this.copyHashTableTo(entry1);
            this.hashArray = entry1;
            this.mask = this.hashArray.length - 1;
            final float f = (float) p_76153_1_;
            this.getClass();
            this.capacity = (int) (f * 0.75F);
        }
    }

    /**
     * copies the hash table to the specified array
     */
    private void copyHashTableTo(final Entry<V>[] p_76154_1_) {
        final Entry<V>[] entry = this.hashArray;
        final int i = p_76154_1_.length;

        for (int j = 0; j < entry.length; ++j) {
            Entry<V> entry1 = entry[j];

            if (entry1 != null) {
                entry[j] = null;

                while (true) {
                    final Entry<V> entry2 = entry1.nextEntry;
                    final int k = getHashIndex(entry1.hash, i - 1);
                    entry1.nextEntry = p_76154_1_[k];
                    p_76154_1_[k] = entry1;
                    entry1 = entry2;

                    if (entry2 == null) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * calls the removeKey method and returns removed object
     */
    public V remove(final long p_76159_1_) {
        final Entry<V> entry = this.removeKey(p_76159_1_);
        return entry == null ? null : entry.value;
    }

    final Entry<V> removeKey(final long p_76152_1_) {
        final int i = getHashedKey(p_76152_1_);
        final int j = getHashIndex(i, this.mask);
        Entry<V> entry = this.hashArray[j];
        Entry<V> entry1;
        Entry<V> entry2;

        for (entry1 = entry; entry1 != null; entry1 = entry2) {
            entry2 = entry1.nextEntry;

            if (entry1.key == p_76152_1_) {
                ++this.modCount;
                --this.numHashElements;

                if (entry == entry1) {
                    this.hashArray[j] = entry2;
                } else {
                    entry.nextEntry = entry2;
                }

                return entry1;
            }

            entry = entry1;
        }

        return entry1;
    }

    /**
     * creates the key in the hash table
     */
    private void createKey(final int p_76156_1_, final long p_76156_2_, final V p_76156_4_, final int p_76156_5_) {
        final Entry<V> entry = this.hashArray[p_76156_5_];
        this.hashArray[p_76156_5_] = new Entry(p_76156_1_, p_76156_2_, p_76156_4_, entry);

        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }

    public double getKeyDistribution() {
        int i = 0;

        for (int j = 0; j < this.hashArray.length; ++j) {
            if (this.hashArray[j] != null) {
                ++i;
            }
        }

        return 1.0D * (double) i / (double) this.numHashElements;
    }

    static class Entry<V> {
        final long key;
        V value;
        Entry<V> nextEntry;
        final int hash;

        Entry(final int p_i1553_1_, final long p_i1553_2_, final V p_i1553_4_, final Entry<V> p_i1553_5_) {
            this.value = p_i1553_4_;
            this.nextEntry = p_i1553_5_;
            this.key = p_i1553_2_;
            this.hash = p_i1553_1_;
        }

        public final long getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final boolean equals(final Object p_equals_1_) {
            if (!(p_equals_1_ instanceof LongHashMap.Entry)) {
                return false;
            } else {
                final Entry<V> entry = (Entry) p_equals_1_;
                final Object object = Long.valueOf(this.getKey());
                final Object object1 = Long.valueOf(entry.getKey());

                if (object == object1 || object != null && object.equals(object1)) {
                    final Object object2 = this.getValue();
                    final Object object3 = entry.getValue();

                    return object2 == object3 || object2 != null && object2.equals(object3);
                }

                return false;
            }
        }

        public final int hashCode() {
            return LongHashMap.getHashedKey(this.key);
        }

        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
}
