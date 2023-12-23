package creeperbabytea.phlib.common.magic.spellwork;

import creeperbabytea.phlib.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.phlib.common.magic.spellwork.item.scroll.ScrollState;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.SpellSet;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Util {
    public static void setWandSpells(ItemStack stack, SpellSet spells) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.remove("spells");
        nbt.put("spells", spells.serializeNBT());
    }

    public static SpellSet getWandSpells(ItemStack stack, Predicate<Spell> filter) {
        SpellSet spells = new SpellSet();
        spells.deserializeNBT(stack.getOrCreateTag().getList("spells", 8), filter);

        return spells;
    }

    public static SpellSet getScrollSpells(ItemStack stack, Predicate<Spell> filter) {
        SpellSet spells;
        if (!(stack.getItem() instanceof ScrollItem)) {
            return new SpellSet();
        }

        ScrollState scroll = ScrollState.from(stack);
        int x = stack.getOrCreateTag().getInt("current_x");
        int y = stack.getOrCreateTag().getInt("current_y");
        List<String> i = scroll.getSlot(x, y).getSpells();
        Spell s = SpellRegistry.getByIncantation(i.get(0));
        spells = new SpellSet(scroll.getSlot(x, y).getSpells().stream().map(SpellRegistry::getByIncantation).filter(filter).collect(Collectors.toList()));

        return spells;
    }
}
