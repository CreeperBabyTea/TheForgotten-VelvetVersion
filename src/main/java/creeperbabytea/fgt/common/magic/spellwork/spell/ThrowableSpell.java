package creeperbabytea.fgt.common.magic.spellwork.spell;

import creeperbabytea.fgt.common.init.ParticleTypes;
import creeperbabytea.fgt.common.magic.general.particles.ParticleSet;
import creeperbabytea.fgt.common.magic.spellwork.entity.SpellEntity;
import net.minecraft.client.world.ClientWorld;
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
        Vector3d oldPos = spellEntity.getOldPositionVec();
        Vector3d pos = spellEntity.getPositionVec();
        int num = (int) ((spellEntity.getMotion().length() + 0.5) * 5 / spellEntity.spellSize()) + 1;
        Vector3d delta = oldPos.subtract(pos).mul(1.0 / num, 1.0 / num, 1.0 / num);

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

    @Override
    public void setColor(int r, int g, int b, int a) {
        super.setColor(r, g, b, a);
        this.setTrailParticle(ParticleSet.builder().put(1, ParticleTypes.COLORED_RIPPLE_2_10.create(r, g, b, a)).put(19, ParticleTypes.COLORED_SQUARE_2.create(r, g, b, a)).build());
    }

    @OnlyIn(Dist.CLIENT)
    public void setTrailParticle(@Nullable ParticleSet trail) {
        this.trail = trail;
    }

    @OnlyIn(Dist.CLIENT)
    public void setHeadParticle(@Nullable ParticleSet head) {
        this.head = head;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public ParticleSet getHeadParticle() {
        return head;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public ParticleSet getTrailParticle() {
        return trail;
    }
}
