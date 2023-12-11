package creeperbabytea.phlib.common.magic.general.wizard.cap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class InnerThoughts implements INBTSerializable<CompoundNBT> {
    private float soul = 1.0F;
    private float rational = 1.0F;
    private float perceptual = 1.0F;

    public float getSoul() {
        return soul;
    }

    public float getRational() {
        return rational;
    }

    public float getPerceptual() {
        return perceptual;
    }

    public float increaseSoul(float amount) {
        this.soul += amount;
        return this.soul;
    }

    public float increaseRational(float amount) {
        this.rational += amount;
        return this.rational;
    }

    public float increasePerceptual(float amount) {
        this.perceptual += amount;
        return this.perceptual;
    }

    public float multiplySoul(float multiplier) {
        this.soul *= multiplier;
        return this.soul;
    }

    public float multiplyRational(float multiplier) {
        this.rational *= multiplier;
        return this.rational;
    }

    public float multiplyPerceptual(float multiplier) {
        this.perceptual *= multiplier;
        return this.perceptual;
    }

    public void markChanged() {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT base = new CompoundNBT();
        base.putFloat("soul", this.soul);
        base.putFloat("rational", this.rational);
        base.putFloat("perceptual",this.perceptual);
        return base;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.soul = nbt.getFloat("soul");
        this.rational = nbt.getFloat("rational");
        this.perceptual = nbt.getFloat("perceptual");
    }

    @Override
    public String toString() {
        return "InnerThoughts{" +
                "soul=" + soul +
                ", rational=" + rational +
                ", perceptual=" + perceptual +
                '}';
    }
}
