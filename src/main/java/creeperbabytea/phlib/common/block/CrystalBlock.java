package creeperbabytea.phlib.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Queue;

public class CrystalBlock extends Block {
    public CrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return super.getSlipperiness(state, world, pos, entity);
    }
}
