package creeperbabytea.fgt.common;

import creeperbabytea.fgt.client.ClientEventHandler;
import creeperbabytea.fgt.client.data.ClientDataEventHandler;
import creeperbabytea.fgt.client.settings.HotKeys;
import creeperbabytea.fgt.common.data.DataEventHandler;
import creeperbabytea.fgt.common.magic.general.wizard.Capabilities;
import creeperbabytea.fgt.common.magic.spellwork.event.SpellWorkEventHandler;
import creeperbabytea.fgt.common.network.Networking;
import creeperbabytea.fgt.common.util.ReloadEventHandler;
import net.minecraftforge.eventbus.api.IEventBus;

public class ForgottenEventHandler {
    public static void addListener(IEventBus mod, IEventBus forge) {
        Networking.addListener(mod);

        ClientDataEventHandler.addListener(mod);
        ClientEventHandler.addListener(mod, forge);
        ReloadEventHandler.addListener(forge);
        HotKeys.addListener(forge);

        DataEventHandler.addListener(mod);
        SpellWorkEventHandler.addListener(mod, forge);
        Capabilities.addListener(mod, forge);
    }
}
