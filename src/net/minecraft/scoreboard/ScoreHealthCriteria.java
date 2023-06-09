package net.minecraft.scoreboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
    public ScoreHealthCriteria(final String name) {
        super(name);
    }

    public int func_96635_a(final List<EntityPlayer> p_96635_1_) {
        float f = 0.0F;

        for (final EntityPlayer entityplayer : p_96635_1_) {
            f += entityplayer.getHealth() + entityplayer.getAbsorptionAmount();
        }

        if (p_96635_1_.size() > 0) {
            f /= (float) p_96635_1_.size();
        }

        return MathHelper.ceiling_float_int(f);
    }

    public boolean isReadOnly() {
        return true;
    }

    public EnumRenderType getRenderType() {
        return EnumRenderType.HEARTS;
    }
}
