package creeperbabytea.phlib.common.magic.spellwork.item.wand;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Arrays;

public class WandState implements INBTSerializable<CompoundNBT> {
    /** Determines the strength of the wand's magic. Range:[0.0F, 2.0F) */
    private float power;
    /** Determines the behavior of the wand when it changes loyalty. Range:[0.0F, 1.0F] */
    private float loyalty;
    /** Determines the durability of the wand. Range:[0, 500) */
    private int sustainability;

    public WandState(float power, float loyality, int sustainability) {
        this.power = power;
        this.sustainability = sustainability;
        this.loyalty = loyality;
    }

    public WandState(WandMaterial.WandWood wood, WandMaterial.WandCore... core) {
        if (core.length > 3)
            throw new IllegalArgumentException("Wand can only have at most 3 core!");
        this.power = wood.getStiffness() * 0.3F + 0.7F * avg(Arrays.stream(core).map(WandMaterial.WandCore::getStrength).toArray(Float[]::new));
        this.loyalty = avg(Arrays.stream(core).map(WandMaterial.WandCore::getLoyalty).toArray(Float[]::new)) - wood.getBetrayal();
        this.loyalty = loyalty < 0 ? 0 : loyalty;
        this.sustainability = (int) (wood.getSustainability() * 0.85 + 0.45 * Arrays.stream(core).mapToInt(WandMaterial.WandCore::getSustainability).sum() / core.length);
    }

    public WandState(ItemStack wand) {
        this.deserializeNBT(wand.getOrCreateTag().getCompound("state"));
    }

    public float getPower() {
        return power;
    }

    public float getLoyalty() {
        return loyalty;
    }

    public int getSustainability() {
        return sustainability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.putFloat("power", this.power);
        base.putFloat("loyalty", this.loyalty);
        base.putInt("sustainability", this.sustainability);
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.power = nbt.getFloat("power");
        this.loyalty = nbt.getFloat("loyalty");
        this.sustainability = nbt.getInt("sustainability");
    }

    private static float avg(Float... val) {
        float sum = 0;
        for (float value : val)
            sum += value;
        return sum / val.length;
    }
}
