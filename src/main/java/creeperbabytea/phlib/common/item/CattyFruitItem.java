package creeperbabytea.phlib.common.item;

import creeperbabytea.phlib.common.init.Items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CattyFruitItem extends Item {
    public CattyFruitItem() {
        super(new Properties().food(new Food.Builder().build()).group(Items.WORLD));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }
}
