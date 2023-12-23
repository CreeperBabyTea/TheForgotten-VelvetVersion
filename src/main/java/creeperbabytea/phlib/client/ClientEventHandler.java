package creeperbabytea.phlib.client;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.client.entity.model.CatTransformationModel;
import creeperbabytea.phlib.common.init.Entities;
import creeperbabytea.phlib.common.init.magic.MagicObjects;
import creeperbabytea.tealib.client.entity.EntityModelRenderer;
import creeperbabytea.tealib.client.entity.NonModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void addListener(IEventBus mod) {
        mod.addListener(ClientEventHandler::onClientSetup);
    }

    public static void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MagicObjects.SPELL_ENTITY, NonModelRenderer::new);
        //TODO:
        RenderingRegistry.registerEntityRenderingHandler(Entities.CAT_TRANSFORMATION, manager -> new EntityModelRenderer<>(manager, TheForgotten.modLocation("textures/entity/functional/cat_transform.png"), new CatTransformationModel()));
    }
}
