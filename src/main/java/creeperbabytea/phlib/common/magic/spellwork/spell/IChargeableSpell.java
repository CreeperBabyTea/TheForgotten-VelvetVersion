package creeperbabytea.phlib.common.magic.spellwork.spell;

public interface IChargeableSpell {
    /**
     * Equations for spell intensity and charge time.
     * @param charge charge time
     * @return Spell intensity. It should be between 0.0 ~ 1.0
     */
    default float chargeEquation(int charge) {
        return (float) Math.pow((double) charge / 60, 1.0D / 3);
    }
}
