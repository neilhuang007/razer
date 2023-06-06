package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
    public static final PropertyEnum<EnumRailDirection> SHAPE = PropertyEnum.create("shape", EnumRailDirection.class);

    protected BlockRail() {
        super(false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumRailDirection.NORTH_SOUTH));
    }

    protected void onNeighborChangedInternal(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (neighborBlock.canProvidePower() && (new Rail(worldIn, pos, state)).countAdjacentRails() == 3) {
            this.func_176564_a(worldIn, pos, state, false);
        }
    }

    public IProperty<EnumRailDirection> getShapeProperty() {
        return SHAPE;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(SHAPE, EnumRailDirection.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(SHAPE).getMetadata();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, SHAPE);
    }
}
