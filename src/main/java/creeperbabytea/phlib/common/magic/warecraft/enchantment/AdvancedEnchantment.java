package creeperbabytea.phlib.common.magic.warecraft.enchantment;

import creeperbabytea.phlib.common.magic.IMagicObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class AdvancedEnchantment extends Enchantment {
    protected AdvancedEnchantment(EquipmentSlotType[] slots) {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, slots);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}
