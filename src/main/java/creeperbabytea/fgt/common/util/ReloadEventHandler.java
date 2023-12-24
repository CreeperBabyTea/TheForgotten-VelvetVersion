package creeperbabytea.fgt.common.util;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ReloadEventHandler {
    public static void addListener(IEventBus forge) {
        forge.addListener((AddReloadListenerEvent event) -> event.addListener(new SpellLanguageJsonReader()));
    }
}
