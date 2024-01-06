package creeperbabytea.fgt.common.magic.general.wizard.cap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellProficiency implements INBTSerializable<CompoundNBT> {
    private float proficiency = 1;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.putFloat("proficiency", proficiency);
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.proficiency = compoundNBT.getFloat("proficiency");
    }

    public void setProficiency(float proficiency) {
        this.proficiency = proficiency;
    }

    public float getProficiency() {
        return proficiency;
    }
}
