package creeperbabytea.phlib.common.magic.spellwork.spell;

import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public abstract class ThrowableSpell extends Spell {
    private ParticleSet trail = ParticleSet.builder().put(1, ParticleTypes.END_ROD).build();
    private ParticleSet head = ParticleSet.builder().put(1, ParticleTypes.EFFECT).build();

    public ThrowableSpell(String incantation, SpellState state) {
        super(incantation, state.setFunction(EnumSpellFunctionType.REMOTE));
    }

    public void onHitBlock(BlockRayTraceResult result, float intensity, SpellEntity spellEntity) {
    }

    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
    }

    /**
     * <p>Draw the trail of a SpellEntity flying in the air. If the SpellEntity represents a combined spell, the trail density of the individual spells is determined by the number of spells in the combined spell. </p>
     * If you don't <p>know what you're doing, don't change this method, but change {@link #drawTrailSingle} below.</p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawTrail(SpellEntity spellEntity) {
        Vector3d pos = spellEntity.getOldPositionVec();
        int num = (int) ((spellEntity.getMotion().length() + 0.5) * 20 / spellEntity.spellSize()) + 1;
        Vector3d delta = spellEntity.getPositionVec().subtract(pos).mul(1.0D / num, 1.0D / num, 1.0D / num);

        for (int i = 0; i < num; i++) {
            pos = pos.add(delta);
            this.drawTrailSingle(pos, (ClientWorld) spellEntity.world);
        }
    }

    /**
     * <p>Draw individual particles in the trail. Generally if you're not looking for something too fancy, but you want something visually appealing, change this.</p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawTrailSingle(Vector3d position, ClientWorld world) {
        if (this.trail != null)
            this.trail.draw(world, true, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);
    }

    /**
     * <p>Draw particles around the SpellEntity itself. The others are the same as {@link #drawTrail}</p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawHead(SpellEntity spellEntity) {
        Vector3d pos = spellEntity.getPositionVec();
        Vector3d velocity = spellEntity.getMotion();
        this.drawHeadSingle(pos, velocity, (ClientWorld) spellEntity.world);
    }

    /**
     * <p>The same as {@link #drawTrailSingle}</p>
     */
    @OnlyIn(Dist.CLIENT)
    public void drawHeadSingle(Vector3d position, Vector3d velocity, ClientWorld world) {
        if (this.head != null)
            this.head.draw(world, true, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z);
    }

    public void setTrailParticle(@Nullable ParticleSet trail) {
        this.trail = trail;
    }

    public void setHeadParticle(@Nullable ParticleSet head) {
        this.head = head;
    }

    @Nullable
    public ParticleSet getTrail() {
        return trail;
    }

    @Nullable
    public ParticleSet getHead() {
        return head;
    }

}
