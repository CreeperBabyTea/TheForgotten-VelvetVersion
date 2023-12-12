package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ParticleSet;
import creeperbabytea.phlib.common.magic.general.wizard.Capabilities;
import creeperbabytea.phlib.common.magic.general.wizard.IMagicCapability;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.spell.EnumSpellType;
import creeperbabytea.phlib.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class KillingCurse extends ThrowableSpell implements IChargeableSpell {
    public KillingCurse() {
        super("avada kedavra", new SpellState(8.9F, -1.0F).setType(EnumSpellType.CURSE));
        this.setColor(0, 255, 0, 255);
    }

    @Override
    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (intensity > 0.9)
            result.getEntity().onKillCommand();
    }

    @Override
    public void influenceOnCaster(LivingEntity caster, float intensity) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            LazyOptional<IMagicCapability> capability = player.getCapability(Capabilities.MAGIC_CAPABILITY);
            capability.ifPresent(cap -> cap.innerThoughts().multiplySoul(0.6F));
        }
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public Vector3d[] chargeParticleEquation(int tick, LivingEntity owner, ItemStack wand) {
        double d0 = Math.log(0.1 * tick);
        double d1 = Math.log(0.01 * tick);
        double d2 = Math.cos(d0);
        double d3 = Math.sin(d0);
        double d4 = Math.cos(d1);
        double d5 = Math.sin(d1);
        double x = d2 * d0;
        double y = d3 * d4;
        double z = -d3 * d5;
        return new Vector3d[]{new Vector3d(x, y, z)};
    }
}
