package creeperbabytea.phlib.common.magic.spellwork.event;

import creeperbabytea.phlib.client.spell.ClientSpellTranslation;
import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import creeperbabytea.phlib.common.magic.spellwork.network.ShoutIncantationPack;
import creeperbabytea.phlib.common.network.Networking;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;
import java.util.Objects;

public class SpellWorkEventHandler {
    public static void addListener(IEventBus mod, IEventBus forge) {
        mod.addListener((FMLCommonSetupEvent event) -> SpellRegistry.reloadMap());
        forge.addListener(SpellWorkEventHandler::onItemExpired);
        forge.addListener(SpellWorkEventHandler::onClientChat);
    }

    public static void onItemExpired(ItemExpireEvent event) {
        if (event.getEntityItem().getItem().getItem() instanceof WandItem) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onClientChat(ClientChatEvent event) {
        System.out.println("onServerChat");
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof WandItem) {
            List<String> spells = ClientSpellTranslation.translateAll(ClientSpellTranslation.formatClientMsg(event.getMessage()));
            if (!spells.isEmpty() && spells.stream().allMatch(Objects::nonNull)) {
                Networking.INSTANCE.sendToServer(new ShoutIncantationPack(spells));
            }
        }
    }
}
