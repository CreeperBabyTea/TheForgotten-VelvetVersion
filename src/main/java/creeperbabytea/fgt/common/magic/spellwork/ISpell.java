package creeperbabytea.fgt.common.magic.spellwork;

import creeperbabytea.fgt.common.magic.IMagicObject;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellState;

public interface ISpell extends IMagicObject {
    @Override
    SpellState getMagicState();
}
