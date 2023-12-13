package creeperbabytea.phlib.common.magic.spellwork.entity;

import creeperbabytea.phlib.common.init.magic.MagicObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class ConflictSpellEntity extends Entity {
    private final List<String> spellA = new ArrayList<>();
    private final List<String> spellB = new ArrayList<>();
    private UUID ownerA;
    private UUID ownerB;
    private int ownerAId;
    private int ownerBId;

    public ConflictSpellEntity(SpellEntity A, SpellEntity B) {
        super(MagicObjects.CONFLICT_SPELL, A.world);
       // this.spellA.addAll(A.getSpells());
        //this.spellB.addAll(B.getSpells());
        this.ownerA = A.getOwner() != null ? A.getOwner().getUniqueID() : null;
        this.ownerB = B.getOwner() != null ? B.getOwner().getUniqueID() : null;
        this.ownerAId = A.getOwner().getEntityId();
        this.ownerBId = B.getOwner().getEntityId();

        this.setPosition(A.getPosX(), A.getPosY(), A.getPosZ());
    }

    @Override
    public void tick() {
        LivingEntity A = getOwnerA();
        LivingEntity B = getOwnerB();
    }

    public ConflictSpellEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.ownerA = compound.getUniqueId("owner_a");
        this.ownerB = compound.getUniqueId("owner_b");
        this.ownerAId = compound.getInt("owner_a_id");
        this.ownerBId = compound.getInt("owner_b_id");
        ListNBT listA = compound.getList("spell_a", 8);
        IntStream.range(0, listA.size()).forEachOrdered(i -> this.spellA.add(listA.getString(i)));
        ListNBT listB = compound.getList("spell_b", 8);
        IntStream.range(0, listB.size()).forEachOrdered(i -> this.spellB.add(listB.getString(i)));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putUniqueId("owner_a", this.ownerA);
        compound.putUniqueId("owner_b", this.ownerB);
        compound.putInt("owner_a_id", this.ownerAId);
        compound.putInt("owner_b_id", this.ownerBId);
        ListNBT listA = new ListNBT();
        spellA.forEach(s -> listA.add(StringNBT.valueOf(s)));
        compound.put("spell_a", listA);
        ListNBT listB = new ListNBT();
        spellB.forEach(s -> listB.add(StringNBT.valueOf(s)));
        compound.put("spell_b", listB);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Nullable
    public LivingEntity getOwnerA() {
        return (LivingEntity) this.world.getEntityByID(this.ownerAId);
    }

    @Nullable
    public LivingEntity getOwnerB() {
        return (LivingEntity) this.world.getEntityByID(this.ownerBId);
    }
}
