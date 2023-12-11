package creeperbabytea.phlib.common.magic.spellwork.item.wand;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class WandState implements INBTSerializable<CompoundNBT> {
    /** Determines the strength of the wand's magic. Range:[0.0F, 2.0F) */
    private final float power;
    /** Determines the behavior of the wand when it changes loyalty. Range:[0.0F, 1.0F]*/
    private final float loyalty;
    /** Determines the durability of the wand. Range:[0, 500) */
    private final int sustainability;

    public WandState(float power, float loyality, int sustainability) {
        this.power = power;
        this.sustainability = sustainability;
        this.loyalty = loyality;
    }

    public WandState(WandMaterial.WandWood wood, WandMaterial.WandCore... core) {
        if (core.length > 3)
            throw new IllegalArgumentException("Wand can only have at most 3 core!");
        float a
        this.power = wood.getStiffness() * 0.3F + a.get();

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
