package creeperbabytea.phlib.common.magic.spellwork.spell;

import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.ISpell;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.tealib.util.math.SpatialVectors;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public abstract class Spell extends ForgeRegistryEntry<Spell> implements ISpell {
    protected final String incantation;
    protected final SpellState state;
    private ParticleSet cast = ParticleSet.builder().put(1, ParticleTypes.ENCHANT).build();

    public Spell(String incantation, SpellState state) {
        this.incantation = incantation.toLowerCase();
        this.state = state;
    }

    @Override
    public SpellState getMagicState() {
        return this.state;
    }

    public String getIncantation() {
        return incantation;
    }

    public void influenceOnCaster(LivingEntity caster, float intensity) {
    }

    /**
     * <p>Special effects drawn while the wand is charging. </p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawCastEffect(ItemStack wand, LivingEntity owner, int tick) {
        ParticleSet cast = this.getCast(owner, tick);
        if (cast == null)
            return;
        Vector3d[] relativePos = this.castParticleEquation(tick, owner, wand);
        if (relativePos != null) {
            for (Vector3d d : relativePos) {
                Vector3d pos = SpatialVectors.rotate(d, owner).add(owner.getPosX(), owner.getPosY() + owner.getEyeHeight(), owner.getPosZ());
                cast.draw((ClientWorld) owner.world, true, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Vector3d[] castParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void setCastParticle(@Nullable ParticleSet cast) {
        this.cast = cast;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public ParticleSet getCast(LivingEntity owner, int tick) {
        return this.cast;
    }

    @Override
    public String toString() {
        return "Spell(" + getRegistryName() + ")";
    }
}
