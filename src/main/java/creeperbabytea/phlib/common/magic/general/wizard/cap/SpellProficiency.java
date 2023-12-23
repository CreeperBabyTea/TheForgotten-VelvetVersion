package creeperbabytea.phlib.common.magic.general.wizard.cap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class SpellProficiency implements INBTSerializable<CompoundNBT> {
    private int proficiency = 0;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.putInt("proficiency", proficiency);
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.proficiency = compoundNBT.getInt("proficiency");
    }
}
