package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.spell.EnumSpellType;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class SlowFallingCharm extends Spell {
    public SlowFallingCharm() {
        super("similis nebulas", new SpellState(2.8F, 0.3F).setType(EnumSpellType.CHARM));
        this.setChargeParticle(ParticleSet.builder().put(1, ParticleTypes.CLOUD).build());
    }

    @Override
    public void onLocalCast(PlayerEntity player, float intensity) {
        player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, (int) (intensity * 20)));
    }

    @Nullable
    @Override
    public Vector3d[] chargeParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        return super.chargeParticleEquation(tick, owner, wand);
        //TODO
    }
}
