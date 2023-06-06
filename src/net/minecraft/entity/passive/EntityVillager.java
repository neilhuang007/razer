package net.minecraft.entity.passive;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Random;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;

    /**
     * This villager's current customer.
     */
    private EntityPlayer buyingPlayer;

    /**
     * Initialises the MerchantRecipeList.java
     */
    private MerchantRecipeList buyingList;
    private int timeUntilReset;

    /**
     * addDefaultEquipmentAndRecipies is called if this is true
     */
    private boolean needsInitilization;
    private boolean isWillingToTrade;
    private int wealth;

    /**
     * Last player to trade with this villager, used for aggressivity.
     */
    private String lastBuyingPlayer;
    private int careerId;

    /**
     * This is the EntityVillager's career level value
     */
    private int careerLevel;
    private boolean isLookingForHome;
    private boolean areAdditionalTasksSet;
    private final InventoryBasic villagerInventory;

    /**
     * A multi-dimensional array mapping the various professions, careers and career levels that a Villager may offer
     */
    private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][]{{{{new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2))}, {new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2))}, {new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7))}, {new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1))}}, {{new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6))}, {new ListEnchantedItemForEmeralds(Items.fishing_rod, new PriceInfo(7, 8))}}, {{new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds(Items.shears, new PriceInfo(3, 4))}, {new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2))}}, {{new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8))}, {new ListItemForEmeralds(Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10))}}}, {{{new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds()}, {new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4))}, {new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3))}, {new ListEnchantedBookForEmeralds()}, {new ListEnchantedBookForEmeralds()}, {new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22))}}}, {{{new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10))}, {new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1))}, {new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1))}, {new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11))}}}, {{{new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_helmet, new PriceInfo(4, 6))}, {new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds(Items.iron_chestplate, new PriceInfo(10, 14))}, {new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_chestplate, new PriceInfo(16, 19))}, {new ListItemForEmeralds(Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds(Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_chestplate, new PriceInfo(11, 15))}}, {{new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8))}, {new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10))}, {new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12))}}, {{new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7))}, {new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11))}, {new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15))}}}, {{{new EmeraldForItems(Items.beef, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18))}, {new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_beef, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6))}}, {{new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds(Items.leather_leggings, new PriceInfo(2, 4))}, {new ListEnchantedItemForEmeralds(Items.leather_chestplate, new PriceInfo(7, 12))}, {new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10))}}}};

    public EntityVillager(final World worldIn) {
        this(worldIn, 0);
    }

    public EntityVillager(final World worldIn, final int professionId) {
        super(worldIn);
        this.villagerInventory = new InventoryBasic("Items", false, 8);
        this.setProfession(professionId);
        this.setSize(0.6F, 1.8F);
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.setCanPickUpLoot(true);
    }

    private void setAdditionalAItasks() {
        if (!this.areAdditionalTasksSet) {
            this.areAdditionalTasksSet = true;

            if (this.isChild()) {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
            } else if (this.getProfession() == 0) {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
            }
        }
    }

    /**
     * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
     * an adult)
     */
    protected void onGrowingAdult() {
        if (this.getProfession() == 0) {
            this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6D));
        }

        super.onGrowingAdult();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }

    protected void updateAITasks() {
        if (--this.randomTickDivider <= 0) {
            final BlockPos blockpos = new BlockPos(this);
            this.worldObj.getVillageCollection().addToVillagerPositionList(blockpos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockpos, 32);

            if (this.villageObj == null) {
                this.detachHome();
            } else {
                final BlockPos blockpos1 = this.villageObj.getCenter();
                this.setHomePosAndDistance(blockpos1, (int) ((float) this.villageObj.getVillageRadius() * 1.0F));

                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;

            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    for (final MerchantRecipe merchantrecipe : this.buyingList) {
                        if (merchantrecipe.isRecipeDisabled()) {
                            merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }

                    this.populateBuyingList();
                    this.needsInitilization = false;

                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte) 14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }

        super.updateAITasks();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(final EntityPlayer player) {
        final ItemStack itemstack = player.inventory.getCurrentItem();
        final boolean flag = itemstack != null && itemstack.getItem() == Items.spawn_egg;

        if (!flag && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            }

            player.triggerAchievement(StatList.timesTalkedToVillagerStat);
            return true;
        } else {
            return super.interact(player);
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Integer.valueOf(0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(final NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Profession", this.getProfession());
        tagCompound.setInteger("Riches", this.wealth);
        tagCompound.setInteger("Career", this.careerId);
        tagCompound.setInteger("CareerLevel", this.careerLevel);
        tagCompound.setBoolean("Willing", this.isWillingToTrade);

        if (this.buyingList != null) {
            tagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }

        final NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.villagerInventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

            if (itemstack != null) {
                nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
            }
        }

        tagCompound.setTag("Inventory", nbttaglist);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(final NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.setProfession(tagCompund.getInteger("Profession"));
        this.wealth = tagCompund.getInteger("Riches");
        this.careerId = tagCompund.getInteger("Career");
        this.careerLevel = tagCompund.getInteger("CareerLevel");
        this.isWillingToTrade = tagCompund.getBoolean("Willing");

        if (tagCompund.hasKey("Offers", 10)) {
            final NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(nbttagcompound);
        }

        final NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            final ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));

            if (itemstack != null) {
                this.villagerInventory.func_174894_a(itemstack);
            }
        }

        this.setCanPickUpLoot(true);
        this.setAdditionalAItasks();
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn() {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return "mob.villager.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return "mob.villager.death";
    }

    public void setProfession(final int professionId) {
        this.dataWatcher.updateObject(16, Integer.valueOf(professionId));
    }

    public int getProfession() {
        return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
    }

    public boolean isMating() {
        return this.isMating;
    }

    public void setMating(final boolean mating) {
        this.isMating = mating;
    }

    public void setPlaying(final boolean playing) {
        this.isPlaying = playing;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setRevengeTarget(final EntityLivingBase livingBase) {
        super.setRevengeTarget(livingBase);

        if (this.villageObj != null && livingBase != null) {
            this.villageObj.addOrRenewAgressor(livingBase);

            if (livingBase instanceof EntityPlayer) {
                int i = -1;

                if (this.isChild()) {
                    i = -3;
                }

                this.villageObj.setReputationForPlayer(livingBase.getCommandSenderName(), i);

                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte) 13);
                }
            }
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(final DamageSource cause) {
        if (this.villageObj != null) {
            final Entity entity = cause.getEntity();

            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(entity.getCommandSenderName(), -2);
                } else if (entity instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            } else {
                final EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 16.0D);

                if (entityplayer != null) {
                    this.villageObj.endMatingSeason();
                }
            }
        }

        super.onDeath(cause);
    }

    public void setCustomer(final EntityPlayer p_70932_1_) {
        this.buyingPlayer = p_70932_1_;
    }

    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    public boolean isTrading() {
        return this.buyingPlayer != null;
    }

    /**
     * Returns current or updated value of {@link #isWillingToTrade}
     *
     * @param updateFirst Updates {@link #isWillingToTrade} variable if set to true
     */
    public boolean getIsWillingToTrade(final boolean updateFirst) {
        if (!this.isWillingToTrade && updateFirst && this.func_175553_cp()) {
            boolean flag = false;

            for (int i = 0; i < this.villagerInventory.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

                if (itemstack != null) {
                    if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3) {
                        flag = true;
                        this.villagerInventory.decrStackSize(i, 3);
                    } else if ((itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot) && itemstack.stackSize >= 12) {
                        flag = true;
                        this.villagerInventory.decrStackSize(i, 12);
                    }
                }

                if (flag) {
                    this.worldObj.setEntityState(this, (byte) 18);
                    this.isWillingToTrade = true;
                    break;
                }
            }
        }

        return this.isWillingToTrade;
    }

    public void setIsWillingToTrade(final boolean willingToTrade) {
        this.isWillingToTrade = willingToTrade;
    }

    public void useRecipe(final MerchantRecipe recipe) {
        recipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
        int i = 3 + this.rand.nextInt(4);

        if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            this.isWillingToTrade = true;

            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            } else {
                this.lastBuyingPlayer = null;
            }

            i += 5;
        }

        if (recipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += recipe.getItemToBuy().stackSize;
        }

        if (recipe.getRewardsExp()) {
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, i));
        }
    }

    /**
     * Notifies the merchant of a possible merchantrecipe being fulfilled or not. Usually, this is just a sound byte
     * being played depending if the suggested itemstack is not null.
     */
    public void verifySellingItem(final ItemStack stack) {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();

            if (stack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            } else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    public MerchantRecipeList getRecipes(final EntityPlayer p_70934_1_) {
        if (this.buyingList == null) {
            this.populateBuyingList();
        }

        return this.buyingList;
    }

    private void populateBuyingList() {
        final ITradeList[][][] aentityvillager$itradelist = DEFAULT_TRADE_LIST_MAP[this.getProfession()];

        if (this.careerId != 0 && this.careerLevel != 0) {
            ++this.careerLevel;
        } else {
            this.careerId = this.rand.nextInt(aentityvillager$itradelist.length) + 1;
            this.careerLevel = 1;
        }

        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }

        final int i = this.careerId - 1;
        final int j = this.careerLevel - 1;
        final ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];

        if (j >= 0 && j < aentityvillager$itradelist1.length) {
            final ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j];

            for (final ITradeList entityvillager$itradelist : aentityvillager$itradelist2) {
                entityvillager$itradelist.modifyMerchantRecipeList(this.buyingList, this.rand);
            }
        }
    }

    public void setRecipes(final MerchantRecipeList recipeList) {
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public IChatComponent getDisplayName() {
        final String s = this.getCustomNameTag();

        if (s != null && s.length() > 0) {
            final ChatComponentText chatcomponenttext = new ChatComponentText(s);
            chatcomponenttext.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatcomponenttext.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatcomponenttext;
        } else {
            if (this.buyingList == null) {
                this.populateBuyingList();
            }

            String s1 = null;

            switch (this.getProfession()) {
                case 0:
                    if (this.careerId == 1) {
                        s1 = "farmer";
                    } else if (this.careerId == 2) {
                        s1 = "fisherman";
                    } else if (this.careerId == 3) {
                        s1 = "shepherd";
                    } else if (this.careerId == 4) {
                        s1 = "fletcher";
                    }

                    break;

                case 1:
                    s1 = "librarian";
                    break;

                case 2:
                    s1 = "cleric";
                    break;

                case 3:
                    if (this.careerId == 1) {
                        s1 = "armor";
                    } else if (this.careerId == 2) {
                        s1 = "weapon";
                    } else if (this.careerId == 3) {
                        s1 = "tool";
                    }

                    break;

                case 4:
                    if (this.careerId == 1) {
                        s1 = "butcher";
                    } else if (this.careerId == 2) {
                        s1 = "leather";
                    }
            }

            if (s1 != null) {
                final ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1);
                chatcomponenttranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
                chatcomponenttranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
                return chatcomponenttranslation;
            } else {
                return super.getDisplayName();
            }
        }
    }

    public float getEyeHeight() {
        float f = 1.62F;

        if (this.isChild()) {
            f = (float) ((double) f - 0.81D);
        }

        return f;
    }

    public void handleHealthUpdate(final byte id) {
        if (id == 12) {
            this.spawnParticles(EnumParticleTypes.HEART);
        } else if (id == 13) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        } else if (id == 14) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        } else {
            super.handleHealthUpdate(id);
        }
    }

    private void spawnParticles(final EnumParticleTypes particleType) {
        for (int i = 0; i < 5; ++i) {
            final double d0 = this.rand.nextGaussian() * 0.02D;
            final double d1 = this.rand.nextGaussian() * 0.02D;
            final double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(particleType, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 1.0D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setProfession(this.worldObj.rand.nextInt(5));
        this.setAdditionalAItasks();
        return livingdata;
    }

    public void setLookingForHome() {
        this.isLookingForHome = true;
    }

    public EntityVillager createChild(final EntityAgeable ageable) {
        final EntityVillager entityvillager = new EntityVillager(this.worldObj);
        entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityvillager)), null);
        return entityvillager;
    }

    public boolean allowLeashing() {
        return false;
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        if (!this.worldObj.isRemote && !this.isDead) {
            final EntityWitch entitywitch = new EntityWitch(this.worldObj);
            entitywitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitywitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entitywitch)), null);
            entitywitch.setNoAI(this.isAIDisabled());

            if (this.hasCustomName()) {
                entitywitch.setCustomNameTag(this.getCustomNameTag());
                entitywitch.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }

            this.worldObj.spawnEntityInWorld(entitywitch);
            this.setDead();
        }
    }

    public InventoryBasic getVillagerInventory() {
        return this.villagerInventory;
    }

    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
     * better.
     */
    protected void updateEquipmentIfNeeded(final EntityItem itemEntity) {
        final ItemStack itemstack = itemEntity.getEntityItem();
        final Item item = itemstack.getItem();

        if (this.canVillagerPickupItem(item)) {
            final ItemStack itemstack1 = this.villagerInventory.func_174894_a(itemstack);

            if (itemstack1 == null) {
                itemEntity.setDead();
            } else {
                itemstack.stackSize = itemstack1.stackSize;
            }
        }
    }

    private boolean canVillagerPickupItem(final Item itemIn) {
        return itemIn == Items.bread || itemIn == Items.potato || itemIn == Items.carrot || itemIn == Items.wheat || itemIn == Items.wheat_seeds;
    }

    public boolean func_175553_cp() {
        return this.hasEnoughItems(1);
    }

    /**
     * Used by {@link EntityAIVillagerInteract EntityAIVillagerInteract} to check if the
     * villager can give some items from an inventory to another villager.
     */
    public boolean canAbondonItems() {
        return this.hasEnoughItems(2);
    }

    public boolean func_175557_cr() {
        final boolean flag = this.getProfession() == 0;
        return flag ? !this.hasEnoughItems(5) : !this.hasEnoughItems(1);
    }

    /**
     * Returns true if villager has enough items in inventory
     *
     * @param multiplier requirement multiplier
     */
    private boolean hasEnoughItems(final int multiplier) {
        final boolean flag = this.getProfession() == 0;

        for (int i = 0; i < this.villagerInventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

            if (itemstack != null) {
                if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3 * multiplier || itemstack.getItem() == Items.potato && itemstack.stackSize >= 12 * multiplier || itemstack.getItem() == Items.carrot && itemstack.stackSize >= 12 * multiplier) {
                    return true;
                }

                if (flag && itemstack.getItem() == Items.wheat && itemstack.stackSize >= 9 * multiplier) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if villager has seeds, potatoes or carrots in inventory
     */
    public boolean isFarmItemInInventory() {
        for (int i = 0; i < this.villagerInventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

            if (itemstack != null && (itemstack.getItem() == Items.wheat_seeds || itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot)) {
                return true;
            }
        }

        return false;
    }

    public boolean replaceItemInInventory(final int inventorySlot, final ItemStack itemStackIn) {
        if (super.replaceItemInInventory(inventorySlot, itemStackIn)) {
            return true;
        } else {
            final int i = inventorySlot - 300;

            if (i >= 0 && i < this.villagerInventory.getSizeInventory()) {
                this.villagerInventory.setInventorySlotContents(i, itemStackIn);
                return true;
            } else {
                return false;
            }
        }
    }

    static class EmeraldForItems implements ITradeList {
        public Item sellItem;
        public PriceInfo price;

        public EmeraldForItems(final Item itemIn, final PriceInfo priceIn) {
            this.sellItem = itemIn;
            this.price = priceIn;
        }

        public void modifyMerchantRecipeList(final MerchantRecipeList recipeList, final Random random) {
            int i = 1;

            if (this.price != null) {
                i = this.price.getPrice(random);
            }

            recipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.emerald));
        }
    }

    interface ITradeList {
        void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random);
    }

    static class ItemAndEmeraldToItem implements ITradeList {
        public ItemStack field_179411_a;
        public PriceInfo field_179409_b;
        public ItemStack field_179410_c;
        public PriceInfo field_179408_d;

        public ItemAndEmeraldToItem(final Item p_i45813_1_, final PriceInfo p_i45813_2_, final Item p_i45813_3_, final PriceInfo p_i45813_4_) {
            this.field_179411_a = new ItemStack(p_i45813_1_);
            this.field_179409_b = p_i45813_2_;
            this.field_179410_c = new ItemStack(p_i45813_3_);
            this.field_179408_d = p_i45813_4_;
        }

        public void modifyMerchantRecipeList(final MerchantRecipeList recipeList, final Random random) {
            int i = 1;

            if (this.field_179409_b != null) {
                i = this.field_179409_b.getPrice(random);
            }

            int j = 1;

            if (this.field_179408_d != null) {
                j = this.field_179408_d.getPrice(random);
            }

            recipeList.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), i, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), j, this.field_179410_c.getMetadata())));
        }
    }

    static class ListEnchantedBookForEmeralds implements ITradeList {
        public void modifyMerchantRecipeList(final MerchantRecipeList recipeList, final Random random) {
            final Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
            final int i = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            final ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;

            if (j > 64) {
                j = 64;
            }

            recipeList.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, j), itemstack));
        }
    }

    static class ListEnchantedItemForEmeralds implements ITradeList {
        public ItemStack field_179407_a;
        public PriceInfo field_179406_b;

        public ListEnchantedItemForEmeralds(final Item p_i45814_1_, final PriceInfo p_i45814_2_) {
            this.field_179407_a = new ItemStack(p_i45814_1_);
            this.field_179406_b = p_i45814_2_;
        }

        public void modifyMerchantRecipeList(final MerchantRecipeList recipeList, final Random random) {
            int i = 1;

            if (this.field_179406_b != null) {
                i = this.field_179406_b.getPrice(random);
            }

            final ItemStack itemstack = new ItemStack(Items.emerald, i, 0);
            ItemStack itemstack1 = new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata());
            itemstack1 = EnchantmentHelper.addRandomEnchantment(random, itemstack1, 5 + random.nextInt(15));
            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class ListItemForEmeralds implements ITradeList {
        public ItemStack field_179403_a;
        public PriceInfo field_179402_b;

        public ListItemForEmeralds(final Item par1Item, final PriceInfo priceInfo) {
            this.field_179403_a = new ItemStack(par1Item);
            this.field_179402_b = priceInfo;
        }

        public ListItemForEmeralds(final ItemStack stack, final PriceInfo priceInfo) {
            this.field_179403_a = stack;
            this.field_179402_b = priceInfo;
        }

        public void modifyMerchantRecipeList(final MerchantRecipeList recipeList, final Random random) {
            int i = 1;

            if (this.field_179402_b != null) {
                i = this.field_179402_b.getPrice(random);
            }

            final ItemStack itemstack;
            final ItemStack itemstack1;

            if (i < 0) {
                itemstack = new ItemStack(Items.emerald, 1, 0);
                itemstack1 = new ItemStack(this.field_179403_a.getItem(), -i, this.field_179403_a.getMetadata());
            } else {
                itemstack = new ItemStack(Items.emerald, i, 0);
                itemstack1 = new ItemStack(this.field_179403_a.getItem(), 1, this.field_179403_a.getMetadata());
            }

            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    static class PriceInfo extends Tuple<Integer, Integer> {
        public PriceInfo(final int p_i45810_1_, final int p_i45810_2_) {
            super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
        }

        public int getPrice(final Random rand) {
            return this.getFirst().intValue() >= this.getSecond().intValue() ? this.getFirst().intValue() : this.getFirst().intValue() + rand.nextInt(this.getSecond().intValue() - this.getFirst().intValue() + 1);
        }
    }
}
