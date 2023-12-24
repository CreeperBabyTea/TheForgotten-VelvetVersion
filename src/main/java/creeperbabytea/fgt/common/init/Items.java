package creeperbabytea.fgt.common.init;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.item.CattyFruitItem;
import creeperbabytea.tealib.common.registry.TeaGeneralRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Items extends net.minecraft.item.Items {
    public static final ItemGroup WORLD = new ItemGroup("world") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(CATTY_FRUIT);
        }
    };

    public static final CattyFruitItem CATTY_FRUIT;

    public static void init() {
    }

    static {
        TeaGeneralRegister reg = ForgottenObjects.GENERAL;
        reg.bindSuperClass(Item.class);
        CATTY_FRUIT = reg.add("catty_fruit", new CattyFruitItem());
    }
}
