package creeperbabytea.fgt.client;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.client.entity.model.CatTransformationModel;
import creeperbabytea.fgt.client.settings.HotKeys;
import creeperbabytea.fgt.common.init.Entities;
import creeperbabytea.fgt.common.init.magic.MagicObjects;
import creeperbabytea.tealib.client.entity.EntityModelRenderer;
import creeperbabytea.tealib.client.entity.NonModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void addListener(IEventBus mod, IEventBus forge) {
        mod.addListener(ClientEventHandler::onClientSetup);
    }

    public static void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MagicObjects.SPELL_ENTITY, NonModelRenderer::new);
        //TODO:
        RenderingRegistry.registerEntityRenderingHandler(Entities.CAT_TRANSFORMATION, manager -> new EntityModelRenderer<>(manager, TheForgotten.modLocation("textures/entity/functional/cat_transform.png"), new CatTransformationModel()));
    }
}
