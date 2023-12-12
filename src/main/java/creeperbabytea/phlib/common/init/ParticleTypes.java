package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.client.ClientEventHandler;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.general.particles.ColoredParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.RegistryObject;

public class ParticleTypes extends net.minecraft.particles.ParticleTypes {
    //2: 2*2 pixels
    public static final RegistryObject<ColoredParticleType> COLORED_SQUARE_2 = colored("colored_square_2", false);
    //2_10: 2*2 pixels at first, 10*10 pixels at last
    public static final RegistryObject<ColoredParticleType> COLORED_RIPPLE_2_10 = colored("colored_ripple_2_10", false);
    public static final RegistryObject<ColoredParticleType> COLORED_RIPPLE_2_10_16 = colored("colored_ripple_2_10_16", false);

    //Don't forget to register the ParticleFactory


    /**
     * {@link ClientEventHandler#onParticleFactoryRegistration}
     */
    public static void init() {
    }

    private static RegistryObject<ColoredParticleType> colored(String name, boolean alwaysShow) {
        return PhilosophersObjects.PARTICLES.register(name, () -> new ColoredParticleType(alwaysShow));
    }
}
