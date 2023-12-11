package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.spell.CatTransformingCharm;
import creeperbabytea.phlib.common.spell.ExplosionSpell;
import creeperbabytea.phlib.common.spell.KillingCurse;

import java.util.function.Supplier;

public class Spells {
    public static final Supplier<ThrowableSpell> KILLING_CURSE = PhilosophersObjects.SPELLS.register("killing_curse", KillingCurse::new);
    public static final Supplier<ThrowableSpell> CAT_TRANSFORM_CHARM = PhilosophersObjects.SPELLS.register("cat_transform_charm", CatTransformingCharm::new);
    public static final Supplier<ThrowableSpell> EXPLOSION_SPELL = PhilosophersObjects.SPELLS.register("explosion_spell", ExplosionSpell::new);

    public static void init() {
    }
}
