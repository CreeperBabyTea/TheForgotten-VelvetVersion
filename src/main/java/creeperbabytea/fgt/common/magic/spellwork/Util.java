package creeperbabytea.fgt.common.magic.spellwork;

import creeperbabytea.fgt.common.magic.general.wizard.Capabilities;
import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.concurrent.atomic.AtomicReference;
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
        float proficiency = getProficiency(player);
        ItemStack mainHand = player.getHeldItem(Hand.MAIN_HAND);
        ItemStack offHand = player.getHeldItem(Hand.OFF_HAND);
        Predicate<Spell> finalFilter = filter;
        filter = s -> finalFilter.test(s) && s.getMagicState().getComplexity() <= proficiency;
        return getSpells(mainHand, offHand, filter);
    }

    public static float getProficiency(LivingEntity caster) {
        AtomicReference<Float> result = new AtomicReference<>(1.0F);
        caster.getCapability(Capabilities.MAGIC_CAPABILITY).ifPresent(c -> result.set(c.spellProficiency().getProficiency()));
        return result.get();
    }

    public static void promoteProficiency(LivingEntity caster, float step) {
        float pre = getProficiency(caster);//TODO
        float x = (float) Math.pow(pre, 100);
        float post = (float) Math.min((Math.pow(x + step, 1.0D / 100) + 0.5), 10);
        caster.getCapability(Capabilities.MAGIC_CAPABILITY).ifPresent(c -> c.spellProficiency().setProficiency(post));
    }
}
