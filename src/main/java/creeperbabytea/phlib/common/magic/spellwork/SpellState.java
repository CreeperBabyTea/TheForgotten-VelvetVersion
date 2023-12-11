package creeperbabytea.phlib.common.magic.spellwork;

import creeperbabytea.phlib.common.magic.MagicState;
import creeperbabytea.phlib.common.magic.spellwork.spell.*;

public class SpellState extends MagicState {
    private ISpellType spellType = EnumSpellType.NORMAL;
    private ISpellFunctionType functionType = EnumSpellFunctionType.REMOTE;

    public SpellState(float complexity, float grayscale) {
        super(complexity, grayscale);
    }

    public SpellState setType(ISpellType type) {
        this.spellType = type;
        return this;
    }

    public SpellState setFunction(ISpellFunctionType type) {
        this.functionType = type;
        return this;
    }

    public ISpellType getSpellType() {
        return spellType;
    }

    public ISpellFunctionType getFunctionType() {
        return functionType;
    }
}
