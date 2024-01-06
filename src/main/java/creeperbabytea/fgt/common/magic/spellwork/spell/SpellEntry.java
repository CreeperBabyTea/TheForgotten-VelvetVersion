package creeperbabytea.fgt.common.magic.spellwork.spell;

import creeperbabytea.fgt.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;

public class SpellEntry {
    private final Spell spell;
    private float intensity;

    public SpellEntry(Spell spell, float intensity) {
        this.spell = spell;
        this.intensity = intensity;
    }

    public SpellEntry(Spell spell, int charge) {
        if (!(spell instanceof IChargeableSpell))
            throw new IllegalArgumentException("Cannot create a SpellEntry for an unchargeable spell with a charge: " + spell);
        this.spell = spell;
        this.intensity = ((IChargeableSpell) spell).chargeIntensityEquation(charge);
    }

    public SpellEntry(Spell spell, int charge, float multiplier) {
        this.spell = spell;
        this.intensity = spell instanceof IChargeableSpell ? ((IChargeableSpell) spell).chargeIntensityEquation(charge) * multiplier : multiplier;
    }

    public float mulIntensity(float multiplier) {
        if (multiplier < 0)
            return intensity;
        intensity *= multiplier;
        return intensity;
    }

    public Spell get() {
        return spell;
    }

    public float intensity() {
        return this.intensity;
    }
}
