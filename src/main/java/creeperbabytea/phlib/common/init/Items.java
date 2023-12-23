package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.item.CattyFruitItem;
import creeperbabytea.phlib.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.tealib.common.objects.SingleItemEntry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Items extends net.minecraft.item.Items {
    public static final ItemGroup WORLD = new ItemGroup("world") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(CATTY_FRUIT);
        }
    };

    public static final CattyFruitItem CATTY_FRUIT = PhilosophersObjects.ITEMS.add("catty_fruit", new CattyFruitItem());

    public static void init() {
    }
}
