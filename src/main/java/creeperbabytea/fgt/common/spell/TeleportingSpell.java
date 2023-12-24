package creeperbabytea.fgt.common.spell;

import creeperbabytea.fgt.common.init.Effects;
import creeperbabytea.fgt.common.init.ParticleTypes;
import creeperbabytea.fgt.common.magic.general.particles.ParticleSet;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellState;
import creeperbabytea.fgt.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.fgt.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;

public class TeleportingSpell extends ThrowableSpell {
    public TeleportingSpell() {
        super("phantasma transitus", new SpellState(6.7F, 0.0F));
        this.setHeadParticle(ParticleSet.builder().put(1, ParticleTypes.PORTAL).build());
        this.setTrailParticle(null);
    }

    @Override
    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (spellEntity.getOwner() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) spellEntity.getOwner();
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 10));//TODO
            player.setPosition(result.getEntity().getPosX(), result.getEntity().getPosY(), result.getEntity().getPosZ());
        }
    }

    @Override
    public void onHitBlock(BlockRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (spellEntity.getOwner() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) spellEntity.getOwner();
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 10));//TODO
            player.setPosition(result.getPos().getX(), result.getPos().getY() + 1, result.getPos().getZ());
        }
    }
}
