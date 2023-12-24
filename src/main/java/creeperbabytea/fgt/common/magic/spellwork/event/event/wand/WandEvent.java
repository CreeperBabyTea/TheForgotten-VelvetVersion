package creeperbabytea.fgt.common.magic.spellwork.event.event.wand;

import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;

public class WandEvent extends LivingEvent {
    protected ItemStack wand;
    protected ItemStack scroll;

    public WandEvent(LivingEntity player, ItemStack wand, ItemStack scroll) {
        super(player);
        if (wand.getItem() instanceof WandItem) {
            this.wand = wand;
            if (scroll.getItem() instanceof ScrollItem)
                this.scroll = scroll;
        } else
            throw new IllegalArgumentException("Item in main hand is not a wand: " + wand.getItem());
    }

    public ItemStack getWand() {
        return wand;
    }

    @Nullable
    public ItemStack getScroll() {
        return scroll;
    }
}
