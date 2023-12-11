package creeperbabytea.phlib.common.magic.warecraft.block;

import creeperbabytea.phlib.common.magic.warecraft.item.EnchantDictionaryItem;
import creeperbabytea.phlib.common.magic.warecraft.tileentity.TableOfEnchantmentsTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class TableOfEnchantmentsBlock extends ContainerBlock {
    public TableOfEnchantmentsBlock() {
        super(Properties.create(Material.ROCK).doesNotBlockMovement().notSolid().setLightLevel((a) -> 10));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TableOfEnchantmentsTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote || handIn == Hand.OFF_HAND)
            return ActionResultType.PASS;

        TableOfEnchantmentsTileEntity te = (TableOfEnchantmentsTileEntity) worldIn.getTileEntity(pos);
        assert te != null;
        if (te.hasDictionary()) {
            if (player.isCrouching())
                player.inventory.addItemStackToInventory(te.takeDictionary());
        } else if (player.getHeldItem(handIn).getItem() instanceof EnchantDictionaryItem) {
            te.putDictionary(player.getHeldItem(handIn));
            player.setHeldItem(handIn, ItemStack.EMPTY);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2;
                }

                if (rand.nextInt(16) == 0) {
                    for (int k = 0; k <= 1; ++k) {
                        BlockPos blockpos = pos.add(i, k, j);
                        if (worldIn.getBlockState(blockpos).getEnchantPowerBonus(worldIn, blockpos) > 0.0F) {
                            if (!worldIn.isAirBlock(pos.add(i / 2, 0, j / 2))) {
                                break;
                            }

                            worldIn.addParticle(ParticleTypes.ENCHANT, (double) pos.getX() + 0.5, (double) pos.getY() + 2.0, (double) pos.getZ() + 0.5, (double) ((float) i + rand.nextFloat()) - 0.5, (double) ((float) k - rand.nextFloat() - 1.0F), (double) ((float) j + rand.nextFloat()) - 0.5);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
