package creeperbabytea.fgt.common.world.gen.feature.tree;

import creeperbabytea.fgt.common.init.Blocks;
import creeperbabytea.tealib.common.world.gen.feature.tree.*;
import creeperbabytea.tealib.util.math.WeighedRandBreaker;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class SkywardGazingTree extends TreeFeature {
    public SkywardGazingTree() {
        super(TreeFeatureConfig.CODEC, new Truck(), new Leaves());
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, TreeFeatureConfig config) {
        return super.generate(reader, generator, rand, pos, config);
    }

    public static class Truck extends TruckFeature {
        public Truck() {
            super();
        }

        @Override
        public BlockPos grow(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos blockPos, TreeFeatureConfig treeConfig) {
            TruckFeatureConfig config = treeConfig.getTruck();
            Random rnd = reader.getRandom();
            int height = rnd.nextInt(40 - blockPos.getY()) + 10;

            Map<Direction, WeighedRandBreaker> stretching = new EnumMap<>(Direction.class);
            stretching.put(Direction.EAST, new WeighedRandBreaker(rand, height));
            stretching.put(Direction.WEST, new WeighedRandBreaker(rand, height));
            stretching.put(Direction.SOUTH, new WeighedRandBreaker(rand, height));
            stretching.put(Direction.NORTH, new WeighedRandBreaker(rand, height));
            for (int i = 0; i < height; i++) {
                reader.setBlockState(blockPos.up(i), config.getTruck(), 3);
                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    if (stretching.get(dir) != null && stretching.get(dir).step()) {
                        place(reader, blockPos.up(i), config.getTruck());
                    } else
                        stretching.remove(dir);
                }
            }
            return blockPos.up(height);
        }
    }

    public static class Leaves extends LeavesFeature {
        public Leaves() {
            super();
        }

        @Override
        public BlockPos grow(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeConfig) {
            Feature.ORE.generate(reader, chunkGenerator, random, blockPos, new OreFeatureConfig(new BlockMatchRuleTest(Blocks.AIR), treeConfig.getLeaves().getLeaves(), 5));

            return blockPos;
        }
    }
}
