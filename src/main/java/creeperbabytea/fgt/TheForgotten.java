package creeperbabytea.fgt;

import creeperbabytea.fgt.common.ForgottenEventHandler;
import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.event.event.wand.WandCastEvent;
import creeperbabytea.tealib.util.IModResourceHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TheForgotten.MODID)
public class TheForgotten implements IModResourceHelper {
    public static final String MODID = "the_forgotten";
    public static final String VERSION = "0.1";

    public static final Logger LOGGER = LogManager.getLogger();
    public static TheForgotten INSTANCE;

    public final IEventBus MOD;
    public final IEventBus FORGE;

    public TheForgotten() {
        MOD = FMLJavaModLoadingContext.get().getModEventBus();
        FORGE = MinecraftForge.EVENT_BUS;

        ForgottenObjects.init(MOD);

        ForgottenEventHandler.addListener(MOD, FORGE);
        FORGE.register(this);

        INSTANCE = this;
    }

    @Override
    public String modId() {
        return MODID;
    }

    public static ResourceLocation modLocation(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static ResourceLocation forgeLocation(String path) {
        return new ResourceLocation("forge", path);
    }

    public static ResourceLocation mcLocation(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
