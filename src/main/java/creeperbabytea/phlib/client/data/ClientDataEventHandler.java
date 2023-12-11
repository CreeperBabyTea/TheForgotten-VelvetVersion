package creeperbabytea.phlib.client.data;

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
    }
}
