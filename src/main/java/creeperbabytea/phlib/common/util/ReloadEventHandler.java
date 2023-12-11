package creeperbabytea.phlib.common.util;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ReloadEventHandler {
    public static void addListener(IEventBus forge) {
        forge.addListener(ReloadEventHandler::onReload);
    }

    public static void onReload(AddReloadListenerEvent event) {
        event.addListener(new SpellLanguageJsonReader());
    }
}
