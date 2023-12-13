package creeperbabytea.phlib.common.init.magic;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.spell.*;

import java.util.function.Supplier;

public class Spells {
    public static final ThrowableSpell KILLING_CURSE = PhilosophersObjects.SPELLS.add("killing_curse", new KillingCurse());
    public static final ThrowableSpell CAT_TRANSFORM_CHARM = PhilosophersObjects.SPELLS.add("cat_transform_charm", new CatTransformingCharm());
    public static final ThrowableSpell EXPLOSION_SPELL = PhilosophersObjects.SPELLS.add("explosion_spell", new ExplosionSpell());
    public static final ThrowableSpell TELEPORTING_SPELL = PhilosophersObjects.SPELLS.add("teleporting_spell", new TeleportingSpell());

    public static final Spell LUMINOUS_SPELL = PhilosophersObjects.SPELLS.add("luminous_spell", new LuminousSpell());
    public static final Spell SLOW_FALLING_CHARM = PhilosophersObjects.SPELLS.add("slow_falling_charm", new SlowFallingCharm());

    public static void init() {
    }
}
