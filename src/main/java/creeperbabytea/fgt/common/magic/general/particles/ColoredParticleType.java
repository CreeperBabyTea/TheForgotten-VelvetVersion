package creeperbabytea.fgt.common.magic.general.particles;

import com.mojang.serialization.Codec;
import creeperbabytea.fgt.client.particle.ColoredParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.ParticleType;

import java.util.function.Supplier;

public class ColoredParticleType extends ParticleType<ColoredParticleData> implements Supplier<ParticleManager.IParticleMetaFactory<ColoredParticleData>> {
    public ColoredParticleType(boolean alwaysShow) {
        super(alwaysShow, ColoredParticleData.DESERIALIZER);
    }

    @Override
    public Codec<ColoredParticleData> func_230522_e_() {
        return ColoredParticleData.CODEC;
    }

    public ColoredParticleData create(int r, int g, int b, int a, float scale, int maxAge) {
        ColoredParticleType data = this;
        return new ColoredParticleData(r, g, b, a, scale, maxAge) {
            @Override
            public ColoredParticleType getType() {
                return data;
            }
        };
    }

    public ColoredParticleData create(int r, int g, int b, int a) {
        return create(r, g, b, a, 1.0F, 30);
    }

    @Override
    public ParticleManager.IParticleMetaFactory<ColoredParticleData> get() {
        return ColoredParticle.Factory::new;
    }
}
