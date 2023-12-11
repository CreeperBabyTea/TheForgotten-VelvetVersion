package creeperbabytea.phlib.common.magic.general.wizard;

import creeperbabytea.phlib.common.magic.general.wizard.cap.InnerThoughts;
import net.minecraft.nbt.CompoundNBT;

public class MagicCapability implements IMagicCapability {
    private final InnerThoughts innerThoughts = new InnerThoughts();

    @Override
    public InnerThoughts innerThoughts() {
        return this.innerThoughts;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.put("inner_thoughts", this.innerThoughts.serializeNBT());
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.innerThoughts.deserializeNBT(nbt.getCompound("inner_thoughts"));
    }
}
