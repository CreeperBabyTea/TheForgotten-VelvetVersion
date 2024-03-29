package creeperbabytea.fgt.common.spell;

import creeperbabytea.fgt.common.magic.spellwork.spell.SpellState;
import creeperbabytea.fgt.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.fgt.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.fgt.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;

public class ExplosionSpell extends ThrowableSpell implements IChargeableSpell {
    public ExplosionSpell() {
        super("detonare", new SpellState(3.3F, -0.4F));
    }

    @Override
    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (!spellEntity.world.isRemote)
            spellEntity.world.createExplosion(spellEntity, result.getEntity().getPosX(), result.getEntity().getPosY(), result.getEntity().getPosZ(), 6.0F * intensity, Explosion.Mode.BREAK);
    }

    @Override
    public void onHitBlock(BlockRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (!spellEntity.world.isRemote)
            spellEntity.world.createExplosion(spellEntity, result.getPos().getX(), result.getPos().getY(), result.getPos().getZ(), 6.0F * intensity, Explosion.Mode.BREAK);
    }
}
