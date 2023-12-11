package creeperbabytea.phlib.common.magic.spellwork.event;

import creeperbabytea.phlib.common.magic.spellwork.Util;
import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

public class SpellWorkEventHandler {
    public static void addListener(IEventBus mod, IEventBus forge) {
        mod.addListener(SpellWorkEventHandler::onCommonSetup);
        forge.addListener(SpellWorkEventHandler::onItemExpired);
        forge.addListener(SpellWorkEventHandler::onServerChat);
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        SpellRegistry.reloadMap();
    }

    public static void onItemExpired(ItemExpireEvent event) {
        if (event.getEntityItem().getItem().getItem() instanceof WandItem) {
            event.setCanceled(true);
        }
    }

    public static void onServerChat(ServerChatEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof WandItem) {
            List<Spell> spells = Util.fromIncantations(event.getMessage());
            ItemStack wand = player.getHeldItem(Hand.MAIN_HAND);
            if (!spells.isEmpty())
                WandItem.setSpells(wand, spells);
        }
    }
}
