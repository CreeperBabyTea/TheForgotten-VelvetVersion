package creeperbabytea.phlib.common.magic.general.wizard;

import creeperbabytea.phlib.common.magic.general.wizard.cap.DarkHeart;
import creeperbabytea.phlib.common.magic.general.wizard.cap.InnerThoughts;
import creeperbabytea.phlib.common.magic.general.wizard.cap.SpellProficiency;
import net.minecraft.nbt.CompoundNBT;

public class MagicCapability implements IMagicCapability {
    private final InnerThoughts innerThoughts = new InnerThoughts();
    private final SpellProficiency spellProficiency = new SpellProficiency();
    private final DarkHeart darkHeart = new DarkHeart();

    @Override
    public InnerThoughts innerThoughts() {
        return this.innerThoughts;
    }

    public SpellProficiency spellProficiency() {
        return spellProficiency;
    }

    public DarkHeart darkHeart() {
        return darkHeart;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.put("inner_thoughts", this.innerThoughts.serializeNBT());
        base.put("spell_proficiency", this.spellProficiency.serializeNBT());
        base.put("dark_heart", this.darkHeart.serializeNBT());
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.innerThoughts.deserializeNBT(nbt.getCompound("inner_thoughts"));
        this.spellProficiency.deserializeNBT(nbt.getCompound("spell_proficiency"));
        this.darkHeart.deserializeNBT(nbt.getCompound("dark_heart"));
    }
}
