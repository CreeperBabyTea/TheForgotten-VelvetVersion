package creeperbabytea.fgt.common.magic.spellwork.spell;

import creeperbabytea.fgt.common.init.ParticleTypes;
import creeperbabytea.fgt.common.magic.general.particles.ParticleSet;
import creeperbabytea.tealib.util.math.SpatialVectors;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public interface IChargeableSpell {
    ParticleSet defaultParticle = ParticleSet.builder().put(1, ParticleTypes.ENCHANT).build();

    /**
     * <p>Special effects drawn while the wand is charging. </p>
     */
    @OnlyIn(Dist.CLIENT)
    default void drawChargeEffect(ItemStack wand, LivingEntity owner, int tick) {
        ParticleSet cast = this.getChargeParticle(owner, tick);
        if (cast == null) return;
        Vector3d[] relativePos = this.chargeParticleEquation(tick, owner, wand);
        if (relativePos != null) {
            for (Vector3d d : relativePos) {
                Vector3d pos;
                if (isRelativePos())
                    pos = SpatialVectors.rotate(d, owner);
                else
                    pos = d;
                pos.add(owner.getPosX(), owner.getPosY() + owner.getEyeHeight(), owner.getPosZ());
                cast.draw((ClientWorld) owner.world, true, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    default ParticleSet getChargeParticle(LivingEntity owner, int tick) {
        return defaultParticle;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    default Vector3d[] chargeParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        return new Vector3d[]{new Vector3d(1, 0, 0)};
    }

    /**
     * This method decides if the particles should be rotated to face the caster.
     */
    default boolean isRelativePos() {
        return true;
    }

    /**
     * Equations for spell intensity and charge time.
     *
     * @param charge charge time
     * @return Spell intensity. It should be between 0.0 ~ 1.0
     */
    @OnlyIn(Dist.CLIENT)
    default float chargeIntensityEquation(int charge) {
        return (float) Math.pow((double) charge / 60, 1.0D / 3);
    }
}
