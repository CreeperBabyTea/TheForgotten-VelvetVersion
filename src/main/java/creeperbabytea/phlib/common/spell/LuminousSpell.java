package creeperbabytea.phlib.common.spell;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import creeperbabytea.phlib.common.init.Blocks;
import creeperbabytea.phlib.common.init.Effects;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class LuminousSpell extends Spell {
    public LuminousSpell() {
        super("luminous", new SpellState(0.0F, 0.0F));
    }

    @Override
    public void onLocalCast(LivingEntity entity, float intensity) {
        if (entity.isPotionActive(Effects.LUMINOUS_MARK)) {
            cleanup(entity, true);
            entity.removeActivePotionEffect(Effects.LUMINOUS_MARK);
        }
        else
            entity.addPotionEffect(new EffectInstance(Effects.LUMINOUS_MARK, 114514, 1, false, false, true));
    }

    private static void cleanup(LivingEntity entity, boolean center) {
        BlockPos pos = entity.getPosition();
        World world = entity.world;
        for (int dx = -2; dx < 3; dx++) {
            for (int dy = -2; dy < 4; dy++) {
                for (int dz = -2; dz < 3; dz++) {
                    BlockPos current = pos.add(dx, dy, dz);
                    if (current != pos || center) {
                        BlockState now = world.getBlockState(current);
                        if (now instanceof LightBlockState) {
                            world.setBlockState(current, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    public static class LuminousMarkEffect extends Effect {
        public LuminousMarkEffect() {
            super(EffectType.BENEFICIAL, 794613);
        }

        @Override
        public void performEffect(LivingEntity entity, int amplifier) {
            if (entity.getHeldItem(Hand.MAIN_HAND).getItem() instanceof WandItem)
                ignite(entity);
            else {
                entity.removeActivePotionEffect(this);
                cleanup(entity, true);
            }
        }

        @Override
        public boolean isReady(int duration, int amplifier) {
            return true;
        }

        private void ignite(LivingEntity entity) {
            BlockPos pos = entity.getPosition();
            World world = entity.world;
            if (world.getBlockState(pos).isAir())
                world.setBlockState(pos, new LightBlockState(world.getBlockState(pos)));
            if (world.getBlockState(pos.up()).isAir())
                world.setBlockState(pos, new LightBlockState(world.getBlockState(pos.up())));
            cleanup(entity, false);
        }
    }

    private static class LightBlockState extends BlockState {
        private final BlockState old;

        public LightBlockState(BlockState old) {
            super(old.getBlock(), old.getValues(), MapCodec.unit(old));
            this.old = old;
        }

        @Override
        public int getLightValue(IBlockReader world, BlockPos pos) {
            return 15;
        }
    }
}
