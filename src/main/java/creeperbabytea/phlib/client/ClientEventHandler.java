package creeperbabytea.phlib.client;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.client.entity.model.CatTransformationModel;
import creeperbabytea.phlib.client.particle.ColoredParticle;
import creeperbabytea.phlib.common.init.Entities;
import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.init.ParticleTypes;
import creeperbabytea.phlib.common.magic.general.particles.ColoredParticleType;
import creeperbabytea.tealib.client.entity.EntityModelRenderer;
import creeperbabytea.tealib.client.entity.NonModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void addListener(IEventBus mod) {
        mod.addListener(ClientEventHandler::onClientSetup);
        mod.addListener(ClientEventHandler::onParticleFactoryRegistration);
    }

    public static void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MagicObjects.SPELL_ENTITY.get(), NonModelRenderer::new);
        //TODO:
        RenderingRegistry.registerEntityRenderingHandler(Entities.CAT_TRANSFORMATION.get(), manager -> new EntityModelRenderer<>(manager, TheForgotten.INSTANCE.modLoc("textures/entity/functional/cat_transform.png"), new CatTransformationModel()));
    }

    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        registerColoredFactory(ParticleTypes.COLORED_SQUARE_2);
        registerColoredFactory(ParticleTypes.COLORED_RIPPLE_2_10);
        registerColoredFactory(ParticleTypes.COLORED_RIPPLE_2_10_16);
    }
    private static void registerColoredFactory(RegistryObject<ColoredParticleType> obj) {
        Minecraft.getInstance().particles.registerFactory(obj.get(), ColoredParticle.Factory::new);
    }
}
