package creeperbabytea.phlib.common.magic.general.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class ColoredParticleType extends ParticleType<ColoredParticleData> {
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
        return create(r, g, b, a, 0.5F, 30);
    }
}
