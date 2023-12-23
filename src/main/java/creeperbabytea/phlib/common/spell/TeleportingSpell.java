package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.init.Effects;
import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import javax.annotation.Nullable;

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
