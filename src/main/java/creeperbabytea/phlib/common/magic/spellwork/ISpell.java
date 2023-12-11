package creeperbabytea.phlib.common.magic.spellwork;

import creeperbabytea.phlib.common.magic.IMagicObject;

public interface ISpell extends IMagicObject {
    @Override
    SpellState getMagicState();
}
