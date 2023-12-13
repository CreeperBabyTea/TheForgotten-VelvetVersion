package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;

public class TeleportingSpell extends ThrowableSpell {
    public TeleportingSpell() {
        super("phantasma transitus", new SpellState(6.7F, 0.0F));
    }

    @Override
    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (spellEntity.getOwner() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) spellEntity.getOwner();
            player.setPosition(result.getEntity().getPosX(), result.getEntity().getPosY(), result.getEntity().getPosZ());
        }
    }

    @Override
    public void onHitBlock(BlockRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (spellEntity.getOwner() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) spellEntity.getOwner();
            player.setPosition(result.getPos().getX(), result.getPos().getY() + 1, result.getPos().getZ());
        }
    }
}
