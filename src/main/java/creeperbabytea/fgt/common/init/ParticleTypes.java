package creeperbabytea.fgt.common.init;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.general.particles.ColoredParticleType;

public class ParticleTypes extends net.minecraft.particles.ParticleTypes {
    //2: 2*2 pixels
    public static final ColoredParticleType COLORED_SQUARE_2 = colored("colored_square_2", false);
    //2_10: 2*2 pixels at first, 10*10 pixels at last
    public static final ColoredParticleType COLORED_RIPPLE_2_10 = colored("colored_ripple_2_10", false);
    public static final ColoredParticleType COLORED_RIPPLE_2_10_16 = colored("colored_ripple_2_10_16", false);

    public static void init() {
    }

    private static ColoredParticleType colored(String name, boolean alwaysShow) {
        return ForgottenObjects.PARTICLES.add(name, new ColoredParticleType(alwaysShow));
    }
}
