package creeperbabytea.fgt.client.data;

import creeperbabytea.fgt.client.data.lang.EN_US;
import creeperbabytea.fgt.client.data.lang.ZH_CN;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ClientDataEventHandler {
    public static void addListener(IEventBus mod) {
        mod.addListener(ClientDataEventHandler::onGatherData);
    }

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        gen.addProvider(new ItemModelProvider(gen, helper));
        gen.addProvider(new EN_US(gen));
        gen.addProvider(new ZH_CN(gen));


    }
}
