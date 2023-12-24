package creeperbabytea.fgt.common.init.magic;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.fgt.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.fgt.common.spell.*;

public class Spells {
    public static final ThrowableSpell KILLING_CURSE = ForgottenObjects.SPELLS.add("killing_curse", new KillingCurse());
    public static final ThrowableSpell CAT_TRANSFORM_CHARM = ForgottenObjects.SPELLS.add("cat_transform_charm", new CatTransformingCharm());
    public static final ThrowableSpell EXPLOSION_SPELL = ForgottenObjects.SPELLS.add("explosion_spell", new ExplosionSpell());
    public static final ThrowableSpell TELEPORTING_SPELL = ForgottenObjects.SPELLS.add("teleporting_spell", new TeleportingSpell());

    public static final Spell LUMINOUS_SPELL = ForgottenObjects.SPELLS.add("luminous_spell", new LuminousSpell());
    public static final Spell SLOW_FALLING_CHARM = ForgottenObjects.SPELLS.add("slow_falling_charm", new SlowFallingCharm());

    public static void init() {
    }
}
