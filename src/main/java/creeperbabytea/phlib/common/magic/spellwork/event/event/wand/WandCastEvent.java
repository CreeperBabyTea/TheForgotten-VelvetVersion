package creeperbabytea.phlib.common.magic.spellwork.event.event.wand;

import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class WandCastEvent extends WandEvent {
    private ItemStack wand;

    public WandCastEvent(LivingEntity player) {
        super(player);
        ItemStack item = player.getHeldItem(Hand.MAIN_HAND);
        if (item.getItem() instanceof WandItem)
            this.wand = item;
        else
            throw new IllegalArgumentException("Item in main hand is not a wand: " + item.getItem());
    }

    public WandCastEvent(LivingEntity player, ItemStack wand) {
        super(player);
        this.wand = wand;
    }

    public static class Pre extends WandCastEvent{
        public Pre(LivingEntity player) {
            super(player);
        }

        public Pre(LivingEntity player, ItemStack wand) {
            super(player, wand);
        }
    }

    public static class Post extends WandCastEvent{
        public Post(LivingEntity player) {
            super(player);
        }

        public Post(LivingEntity player, ItemStack wand) {
            super(player, wand);
        }
    }
}
