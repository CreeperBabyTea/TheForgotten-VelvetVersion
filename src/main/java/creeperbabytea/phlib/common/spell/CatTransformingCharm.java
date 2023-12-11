package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.init.Entities;
import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class CatTransformingCharm extends ThrowableSpell {
    public CatTransformingCharm() {
        super("mobilus catia", new SpellState(3.5F, 0.4F));
    }

    @Override
    public void onHitEntity(EntityRayTraceResult result, float intensity, SpellEntity spellEntity) {
        if (result.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) result.getEntity();
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                CatTransformationEntity cat = new CatTransformationEntity(player);
                player.world.addEntity(cat);
            }
        }
    }

    public static class CatTransformationEntity extends Entity {
        private static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.createKey(CatTransformationEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

        public CatTransformationEntity(PlayerEntity owner) {
            this(Entities.CAT_TRANSFORMATION.get(), owner.world);

            this.dataManager.set(OWNER, Optional.of(owner.getUniqueID()));
            this.setPosition(owner.getPosX(), owner.getPosY() + 2, owner.getPosZ());
        }

        public CatTransformationEntity(EntityType<?> entityTypeIn, World worldIn) {
            super(entityTypeIn, worldIn);
        }

        @Override
        public void tick() {
            if (this.getOwner() == null) {
                return;
            }
            Vector3d pos = this.getOwner().getPositionVec();
            float yew = this.getOwner().rotationYaw;
            float pitch = this.getOwner().rotationPitch;
            this.setPositionAndRotation(pos.x, pos.y + 2, pos.z, yew, pitch);
        }

        @Override
        protected void registerData() {
            this.dataManager.register(OWNER, Optional.empty());
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            this.dataManager.set(OWNER, Optional.of(compound.getUniqueId("owner")));
        }

        @Override
        protected void writeAdditional(CompoundNBT compound) {
            if (this.dataManager.get(OWNER).isPresent())
                compound.putUniqueId("owner", this.dataManager.get(OWNER).get());
        }

        @Override
        public IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

        @Nullable
        private PlayerEntity getOwner() {
            if (this.dataManager.get(OWNER).isPresent())
                return this.world.getPlayerByUuid(this.dataManager.get(OWNER).get());
            return null;
        }
    }
}
