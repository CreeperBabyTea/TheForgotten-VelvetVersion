package creeperbabytea.phlib.common.magic.spellwork.entity;

import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.magic.spellwork.SpellEntry;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpellEntity extends Entity {
    private static final DataParameter<CompoundNBT> SPELLS = EntityDataManager.createKey(SpellEntity.class, DataSerializers.COMPOUND_NBT);
    private UUID owner;
    private int ownerId;
    private boolean leftOwner = false;
    private int lifetime = 0;

    public SpellEntity(LivingEntity owner, SpellEntry... spells) {
        super(MagicObjects.SPELL_ENTITY.get(), owner.world);

        CompoundNBT nbt = new CompoundNBT();
        ListNBT spellsList = new ListNBT();
        for (SpellEntry spell : spells) {
            if (spell.get() instanceof ThrowableSpell) {
                CompoundNBT spellNbt = new CompoundNBT();
                spellNbt.putString("spell", spell.get().getRegistryName().toString());
                spellNbt.putFloat("intensity", spell.intensity());
                spellsList.add(spellNbt);
            }
        }
        nbt.put("spells", spellsList);
        this.dataManager.set(SPELLS, nbt);
        this.glowing = true;
    }

    @Override
    public void remove(boolean keepData) {
        super.remove(keepData);
    }

    public SpellEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(SPELLS, new CompoundNBT());
    }

    public void cast(LivingEntity caster, float speedFactor) {
        speedFactor = speedFactor > 4 ? 4.0F : speedFactor;
        this.setPosition(caster.getPosX(), caster.getPosY() + caster.getEyeHeight() * 0.65, caster.getPosZ());
        this.setMotion(caster.getForward().mul(speedFactor, speedFactor, speedFactor));
        this.owner = caster.getUniqueID();
        this.ownerId = caster.getEntityId();
        caster.world.addEntity(this);
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    public void tick() {
        this.lifetime++;
        if (this.lifetime >= 30)
            this.remove();

        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::canHit);
        /*if (rayTraceResult instanceof EntityRayTraceResult && ((EntityRayTraceResult) rayTraceResult).getEntity() instanceof SpellEntity) {
            SpellEntity other = (SpellEntity) ((EntityRayTraceResult) rayTraceResult).getEntity();
            if (other != this) {
                if (this.getEntityId() > other.getEntityId()) {
                    ConflictSpellEntity conflictSpell = new ConflictSpellEntity(this, other);
                    if (this.world.isRemote())
                        world.addEntity(conflictSpell);
                }
            }
        }*/

        List<SpellEntry> entries = this.getSpells();

        this.hitAction(rayTraceResult, entries);

        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();

        double posX = prevPosX + this.getMotion().x;
        double posY = prevPosY + this.getMotion().y;
        double posZ = prevPosZ + this.getMotion().z;
        this.setPosition(posX, posY, posZ);

        if (this.world.isRemote())
            this.drawParticles(entries);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.owner = compound.getUniqueId("owner");
        this.ownerId = compound.getInt("owner_id");
        this.leftOwner = compound.getBoolean("left_owner");
        this.lifetime = compound.getInt("lifetime");
        this.dataManager.set(SPELLS, compound.getCompound("spells"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putUniqueId("owner", this.owner);
        compound.putInt("owner_id", this.ownerId);
        compound.putBoolean("left_owner", this.leftOwner);
        compound.putInt("lifetime", this.lifetime);
        compound.put("spells", this.dataManager.get(SPELLS));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void testLeftPlayer() {
        Entity owner1 = this.getOwner();
        if (owner1 != null) {
            for (Entity entity1 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (entity1) -> !entity1.isSpectator() && entity1.canBeCollidedWith())) {
                if (entity1.getLowestRidingEntity() == owner1.getLowestRidingEntity()) {
                    this.leftOwner = false;
                }
            }
        }
        this.leftOwner = true;
    }

    private boolean canHit(Entity entityIn) {
        this.testLeftPlayer();
        if (entityIn instanceof SpellEntity && entityIn != this)
            return true;
        else if (!entityIn.isSpectator() && entityIn.isAlive() && entityIn.canBeCollidedWith()) {
            Entity entity = this.getEntity();
            return entityIn != entity && (entity == null || this.leftOwner || !entity.isRidingSameEntity(entityIn));
        } else {
            return false;
        }
    }

    private void hitAction(RayTraceResult result, List<SpellEntry> entries) {
        if (result.getType() == RayTraceResult.Type.MISS)
            return;
        if (result instanceof BlockRayTraceResult) {
            entries.forEach(entry -> ((ThrowableSpell) entry.get()).onHitBlock((BlockRayTraceResult) result, entry.intensity(), this));
        }
        if (result instanceof EntityRayTraceResult) {
            entries.forEach(entry -> ((ThrowableSpell) entry.get()).onHitEntity((EntityRayTraceResult) result, entry.intensity(), this));
        }
        this.remove();
    }

    private void drawParticles(List<SpellEntry> entries) {
        entries.forEach(entry -> {
            ((ThrowableSpell) entry.get()).drawHead(this);
            ((ThrowableSpell) entry.get()).drawTrail(this);
        });
    }

    public Vector3d getOldPositionVec() {
        return new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ);
    }

    @Nullable
    public LivingEntity getOwner() {
        return (LivingEntity) this.world.getEntityByID(this.ownerId);
    }

    public List<SpellEntry> getSpells() {
        ListNBT spellsList = this.dataManager.get(SPELLS).getList("spells", 10);
        return spellsList.stream().map(nbt -> {
            Spell spell = SpellRegistry.getById(((CompoundNBT) nbt).getString("spell"));
            float intensity = ((CompoundNBT) nbt).getFloat("intensity");
            if (spell != null)
                return new SpellEntry(spell, intensity);
            else
                throw new NullPointerException("Missing spell: " + ((CompoundNBT) nbt).getString("spell"));
        }).collect(Collectors.toList());
    }

    public int spellSize() {
        return this.dataManager.get(SPELLS).getList("spells", 10).size();
    }
}
