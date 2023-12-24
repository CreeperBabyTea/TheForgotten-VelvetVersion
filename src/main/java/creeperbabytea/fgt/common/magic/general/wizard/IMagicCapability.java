package creeperbabytea.fgt.common.magic.general.wizard;

import creeperbabytea.fgt.common.magic.general.wizard.cap.InnerThoughts;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMagicCapability extends INBTSerializable<CompoundNBT> {
    InnerThoughts innerThoughts();
}
