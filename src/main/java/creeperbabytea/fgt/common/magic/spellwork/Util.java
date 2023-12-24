package creeperbabytea.fgt.common.magic.spellwork;

import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public class Util {
    public static SpellSet getSpells(ItemStack wand, ItemStack scroll, Predicate<Spell> filter) {
        if (scroll.getItem() instanceof ScrollItem) {
            SpellSet set = ScrollItem.getSpellsFrom(scroll, filter);
            if (!set.isEmpty())
                return set;
        }
        if (wand.getItem() instanceof WandItem) {
            return WandItem.getSpellsFrom(wand, filter);
        }
        return new SpellSet();
    }

    public static SpellSet getSpells(LivingEntity player, Predicate<Spell> filter) {
        ItemStack mainHand = player.getHeldItem(Hand.MAIN_HAND);
        ItemStack offHand = player.getHeldItem(Hand.OFF_HAND);
        return getSpells(mainHand, offHand, filter);
    }
}
