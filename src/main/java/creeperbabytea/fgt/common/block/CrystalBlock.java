package creeperbabytea.fgt.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class CrystalBlock extends Block {
    public CrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return super.getSlipperiness(state, world, pos, entity);
    }
}
