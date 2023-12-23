package creeperbabytea.phlib.common.magic.general.wizard.cap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class DarkHeart implements INBTSerializable<CompoundNBT> {
    private int darkPower = 0;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.putInt("power", this.darkPower);
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.darkPower = compoundNBT.getInt("power");
    }
}
