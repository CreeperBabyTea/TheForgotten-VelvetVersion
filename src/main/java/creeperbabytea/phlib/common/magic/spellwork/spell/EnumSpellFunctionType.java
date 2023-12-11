package creeperbabytea.phlib.common.magic.spellwork.spell;

public enum EnumSpellFunctionType implements ISpellFunctionType {
    /** The spell works at where the caster is. */
    LOCAL,

    /** The spell is to be thrown out as a SpellEntity. */
    REMOTE
}
