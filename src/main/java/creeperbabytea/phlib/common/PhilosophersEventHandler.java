package creeperbabytea.phlib.common;

import creeperbabytea.phlib.client.ClientEventHandler;
import creeperbabytea.phlib.client.data.ClientDataEventHandler;
import creeperbabytea.phlib.common.data.DataEventHandler;
import creeperbabytea.phlib.common.magic.general.wizard.Capabilities;
import creeperbabytea.phlib.common.magic.spellwork.event.SpellWorkEventHandler;
import creeperbabytea.phlib.common.network.Networking;
import creeperbabytea.phlib.common.util.ReloadEventHandler;
import net.minecraftforge.eventbus.api.IEventBus;

public class PhilosophersEventHandler {
    public static void addListener(IEventBus mod, IEventBus forge) {
        Networking.addListener(mod);

        ClientDataEventHandler.addListener(mod);
        ClientEventHandler.addListener(mod);
        ReloadEventHandler.addListener(forge);

        DataEventHandler.addListener(mod);
        SpellWorkEventHandler.addListener(mod, forge);
        Capabilities.addListener(mod, forge);
    }
}
