package creeperbabytea.fgt.common.magic.spellwork.event.event.wand;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class MasterResetEvent extends WandEvent {
    public MasterResetEvent(LivingEntity player, ItemStack wand, ItemStack scroll) {
        super(player, wand, scroll);
    }
}
