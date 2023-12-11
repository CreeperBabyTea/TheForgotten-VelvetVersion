package creeperbabytea.phlib.common.magic.spellwork;

import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.registry.SpellRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    //TODO: translation
    public static List<Spell> fromIncantations(String incantations) {
        incantations = incantations.replaceAll("\\s", "");

        char[] chars = incantations.toCharArray();
        List<Spell> result = new ArrayList<>();

        int pA = 0, pB = 0;
        for (char c : chars) {
            if (c == '|') {
                Spell spell = SpellRegistry.getByIncantation(String.copyValueOf(Arrays.copyOfRange(chars, pA, pB)));
                if (spell != null)
                    result.add(spell);
                pA = pB +1;
            }
            pB ++;
        }
        Spell spell = SpellRegistry.getByIncantation(String.copyValueOf(Arrays.copyOfRange(chars, pA, chars.length)));
        if (spell != null)
            result.add(spell);

        return result;
    }
}
