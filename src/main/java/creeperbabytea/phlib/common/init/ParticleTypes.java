package creeperbabytea.phlib.common.init;

import com.mojang.serialization.Codec;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.general.particles.ColoredParticleData;
import creeperbabytea.phlib.common.magic.general.particles.ColoredParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;

public class ParticleTypes extends net.minecraft.particles.ParticleTypes {
    public static final RegistryObject<ColoredParticleType> COLORED_SMALL_DOT = colored("colored_small_dot", false);
    public static final RegistryObject<ColoredParticleType> COLORED_MINI_SPELL_RIPPLE = colored("colored_mini_spell_ripple", false);

    //Don't forget to register the ParticleFactory

    public static void init() {
    }

    private static RegistryObject<ColoredParticleType> colored(String name, boolean alwaysShow) {
        return PhilosophersObjects.PARTICLES.register(name, () -> new ColoredParticleType(alwaysShow));
    }
}
