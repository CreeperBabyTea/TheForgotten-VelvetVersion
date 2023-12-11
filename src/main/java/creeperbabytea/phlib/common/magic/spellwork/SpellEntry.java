package creeperbabytea.phlib.common.magic.spellwork;

import creeperbabytea.phlib.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;

public class SpellEntry {
    private final Spell spell;
    private final float intensity;

    public SpellEntry(Spell spell, float intensity) {
        this.spell = spell;
        this.intensity = intensity;
    }

    public SpellEntry(Spell spell, int charge) {
        if (!(spell instanceof IChargeableSpell))
            throw new IllegalArgumentException("Cannot create a SpellEntry for an unchargeable spell with a charge: " + spell);
        this.spell = spell;
        this.intensity = ((IChargeableSpell) spell).chargeEquation(charge);
    }

    public  SpellEntry(Spell spell, int charge, float multiplier) {
        if (!(spell instanceof IChargeableSpell))
            throw new IllegalArgumentException("Cannot create a SpellEntry for an unchargeable spell with a charge: " + spell);
        this.spell = spell;
        this.intensity = ((IChargeableSpell) spell).chargeEquation(charge) * multiplier;
    }

    public Spell get() {
        return spell;
    }

    public float intensity() {
        return this.intensity;
    }
}
