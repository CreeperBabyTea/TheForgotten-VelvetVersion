package creeperbabytea.fgt.common.magic.warecraft.item;

import creeperbabytea.fgt.common.init.magic.MagicObjects;
import net.minecraft.item.Item;

public class EnchantDictionaryItem extends Item {
    public EnchantDictionaryItem() {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
    }
}
