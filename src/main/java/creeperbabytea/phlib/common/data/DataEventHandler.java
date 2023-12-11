package creeperbabytea.phlib.common.data;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.data.lang.EN_US;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataEventHandler {
    public static void addListener(IEventBus mod) {
        mod.addListener(DataEventHandler::onGatherData);
    }

    public static void onGatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        gen.addProvider(new EN_US(gen, TheForgotten.MODID, "en_us"));
    }
}
