package creeperbabytea.phlib.common.magic.general.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import java.awt.*;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class ColoredParticleData implements IParticleData {
    private final Color color;
    private final float scale;
    private final int maxAge;

    public static final Codec<ColoredParticleData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.INT.fieldOf("r").forGetter(ColoredParticleData::getRed),
                    Codec.INT.fieldOf("g").forGetter(ColoredParticleData::getGreen),
                    Codec.INT.fieldOf("b").forGetter(ColoredParticleData::getBlue),
                    Codec.INT.fieldOf("a").forGetter(ColoredParticleData::getAlpha),
                    Codec.FLOAT.fieldOf("scale").forGetter(ColoredParticleData::getScale),
                    Codec.INT.fieldOf("age").forGetter(ColoredParticleData::getMaxAge))
            .apply(instance, ColoredParticleData::new));

    public static final IDeserializer<ColoredParticleData> DESERIALIZER = new IDeserializer<ColoredParticleData>() {
        @Override
        public ColoredParticleData deserialize(ParticleType<ColoredParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int r = reader.readInt();
            reader.expect(' ');
            int g = reader.readInt();
            reader.expect(' ');
            int b = reader.readInt();
            reader.expect(' ');
            int a = reader.readInt();
            reader.expect(' ');
            float scale = reader.readFloat();
            reader.expect(' ');
            int age = reader.readInt();
            return new ColoredParticleData(r, g, b, a, scale, age);
        }

        @Override
        public ColoredParticleData read(ParticleType<ColoredParticleData> particleTypeIn, PacketBuffer buffer) {
            int r = buffer.readInt();
            int g = buffer.readInt();
            int b = buffer.readInt();
            int a = buffer.readInt();
            float scale = buffer.readFloat();
            int age = buffer.readInt();
            return new ColoredParticleData(r, g, b, a, scale, age);
        }
    };

    public ColoredParticleData(int red, int green, int blue, int alpha, float scale, int maxAge) {
        this.color = new Color(red, green, blue, alpha);
        this.scale = scale;
        this.maxAge = maxAge;
    }

    @Override
    public ParticleType<?> getType() {
        throw new UnsupportedOperationException("Not implemented: you should override this method before calling it");
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(color.getRed());
        buffer.writeInt(color.getGreen());
        buffer.writeInt(color.getBlue());
        buffer.writeInt(color.getAlpha());
        buffer.writeFloat(this.scale);
        buffer.writeInt(this.maxAge);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %d %d %d %d %.2f %d",
                this.getType().getRegistryName(),
                this.color.getRed(),
                this.color.getGreen(),
                this.color.getBlue(),
                this.color.getAlpha(),
                this.scale,
                this.maxAge);
    }

    public Color getColor() {
        return color;
    }

    public int getRed() {
        return color.getRed();
    }

    public int getGreen() {
        return color.getGreen();
    }

    public int getBlue() {
        return color.getBlue();
    }

    public int getAlpha() {
        return color.getAlpha();
    }

    public float getScale() {
        return this.scale;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public ColoredParticleData copy() {
        return new ColoredParticleData(this.getRed(), this.getGreen(), this.getBlue(), this.getAlpha(), this.getScale(), this.getMaxAge());
    }
}
