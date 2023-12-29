package creeperbabytea.fgt.common.world.gen.feature.tree;

import com.mojang.serialization.Codec;
import creeperbabytea.fgt.common.init.Blocks;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class SkywardGazingTree extends Feature<NoFeatureConfig> {
    public SkywardGazingTree() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Random rnd = reader.getRandom();
        int height = rnd.nextInt(255 - pos.getY());

        Map<Direction, Boolean> stretching = new EnumMap<>(Direction.class);
        for (int i = 0; i < height; i++) {
            reader.setBlockState(pos, Blocks.OAK_LOG.getDefaultState(), 2);
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                //if (rnd.nextDouble() > )
            }
        }
        return false;
    }
}
