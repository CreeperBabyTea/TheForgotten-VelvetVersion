package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.spell.EnumSpellType;
import creeperbabytea.phlib.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class SlowFallingCharm extends Spell implements IChargeableSpell {
    private static final ParticleSet chargeParticle = ParticleSet.builder().put(1, ParticleTypes.CLOUD).build();

    public SlowFallingCharm() {
        super("similis nebulas", new SpellState(2.8F, 0.3F).setType(EnumSpellType.CHARM));
    }

    @Override
    public void onLocalCast(LivingEntity player, float intensity) {
        player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) (intensity * 20)));
    }

    @Nullable
    @Override
    public Vector3d[] chargeParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        Vector3d[] result = new Vector3d[50];
        for (int i = 0; i < 50; i++) {
            double angleArc = 2 * Math.PI / i;
            double x = Math.cos(angleArc);
            double z = Math.sin(angleArc);
            if (tick <= 1)
                result[i] = new Vector3d(x * Math.pow(tick, 2) * 2, 0, z * Math.pow(tick, 2) * 2);
            else
                result[i] = new Vector3d(x, 0, z);
        }
        return result;
    }

    @Override
    public ParticleSet getCastParticle() {
        return chargeParticle;
    }

    @Override
    public boolean isRelativePos() {
        return false;
    }
}
