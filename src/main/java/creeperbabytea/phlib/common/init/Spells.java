package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.spell.*;

import java.util.function.Supplier;

public class Spells {
    public static final Supplier<ThrowableSpell> KILLING_CURSE = PhilosophersObjects.SPELLS.register("killing_curse", KillingCurse::new);
    public static final Supplier<ThrowableSpell> CAT_TRANSFORM_CHARM = PhilosophersObjects.SPELLS.register("cat_transform_charm", CatTransformingCharm::new);
    public static final Supplier<ThrowableSpell> EXPLOSION_SPELL = PhilosophersObjects.SPELLS.register("explosion_spell", ExplosionSpell::new);

    public static final Supplier<Spell> LUMINOUS_SPELL = PhilosophersObjects.SPELLS.register("luminous_spell", LuminousSpell::new);
    public static final Supplier<Spell> SLOW_FALLING_CHARM = PhilosophersObjects.SPELLS.register("slow_falling_charm", SlowFallingCharm::new);

    public static void init() {
    }
}
