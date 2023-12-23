package creeperbabytea.phlib.common.network;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.magic.spellwork.network.SaveScrollPack;
import creeperbabytea.phlib.common.magic.spellwork.network.ShoutIncantationPack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "0.0.1";
    private static int id = 0;

    public static int nextId() {
        return id++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(TheForgotten.modLocation("networking"), () -> VERSION, v -> v.equals(VERSION), v -> v.equals(VERSION));
        INSTANCE.messageBuilder(ShoutIncantationPack.class, nextId()).encoder(ShoutIncantationPack::encode).decoder(ShoutIncantationPack::decode).consumer(ShoutIncantationPack::handler).add();
        INSTANCE.messageBuilder(SaveScrollPack.class, nextId()).encoder(SaveScrollPack::encode).decoder(SaveScrollPack::decode).consumer(SaveScrollPack::handler).add();
    }

    public static void addListener(IEventBus mod) {
        mod.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(Networking::register));
    }
}
