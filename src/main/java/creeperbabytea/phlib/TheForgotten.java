package creeperbabytea.phlib;

import creeperbabytea.phlib.common.PhilosophersEventHandler;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.IModidHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TheForgotten.MODID)
public class TheForgotten implements IModidHelper {
    public static final String MODID = "the_forgotten";
    public static final Logger LOGGER = LogManager.getLogger();
    public static TheForgotten INSTANCE;

    public final IEventBus MOD;
    public final IEventBus FORGE;

    public TheForgotten() {
        MOD = FMLJavaModLoadingContext.get().getModEventBus();
        FORGE = MinecraftForge.EVENT_BUS;

        PhilosophersObjects.init(MOD);

        PhilosophersEventHandler.addListener(MOD, FORGE);
        FORGE.register(this);

        INSTANCE = this;
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Override
    public String modid() {
        return MODID;
    }
}
