package creeperbabytea.phlib.common.magic.spellwork.spell;

import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.ISpell;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import creeperbabytea.tealib.util.math.SpatialVectors;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.awt.Color;

public abstract class Spell extends ForgeRegistryEntry<Spell> implements ISpell {
    protected final String incantation;
    protected final SpellState state;
    private Color color;
    private ParticleSet charge = ParticleSet.builder().put(1, ParticleTypes.ENCHANT).build();
    private ParticleSet cast = ParticleSet.builder().put(1, ParticleTypes.COLORED_RIPPLE_2_10_16.create(255, 255, 255, 255, 1.0F, 50)).build();

    public Spell(String incantation, SpellState state) {
        this.incantation = SpellRegistry.format(incantation);
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

    public void onLocalCast(LivingEntity player, float intensity) {
    }

    /**
     * <p>Special effects drawn while the wand is charging. </p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawChargeEffect(ItemStack wand, LivingEntity owner, int tick) {
        ParticleSet cast = this.getChargeParticle(owner, tick);
        if (cast == null)
            return;
        Vector3d[] relativePos = this.chargeParticleEquation(tick, owner, wand);
        if (relativePos != null) {
            for (Vector3d d : relativePos) {
                Vector3d pos = SpatialVectors.rotate(d, owner).add(owner.getPosX(), owner.getPosY() + owner.getEyeHeight(), owner.getPosZ());
                cast.draw((ClientWorld) owner.world, true, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Vector3d[] chargeParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        return new Vector3d[]{new Vector3d(1, 0, 0)};
    }

    @OnlyIn(Dist.CLIENT)
    public void setColor(int r, int g, int b, int a) {
        this.color = new Color(r, g, b, a);
        this.setCastParticle(ParticleSet.builder().put(1, ParticleTypes.COLORED_RIPPLE_2_10_16.create(r, g, b, a, 1.0F, 50)).build());
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Color getColor() {
        return color;
    }

    @OnlyIn(Dist.CLIENT)
    public void setChargeParticle(@Nullable ParticleSet cast) {
        this.charge = cast;
    }

    @OnlyIn(Dist.CLIENT)
    public void setCastParticle(ParticleSet cast) {
        this.cast = cast;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public ParticleSet getChargeParticle(LivingEntity owner, int tick) {
        return this.charge;
    }

    @OnlyIn(Dist.CLIENT)
    public ParticleSet getCastParticle() {
        return this.cast;
    }

    @Override
    public String toString() {
        return "Spell(" + getRegistryName() + ")";
    }
}
