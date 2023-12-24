package creeperbabytea.fgt.common.magic.spellwork.network;

import creeperbabytea.fgt.common.magic.spellwork.item.WandItem;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellSet;
import creeperbabytea.fgt.common.registry.SpellRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The player yells a spell on the client, gets the translation key of the spell on the client,
 * and passes the translation key to the server to complete spell's operation on the server.
 */
public class ShoutIncantationPack {
    private final List<String> spells;

    public ShoutIncantationPack(List<String> spells) {
        this.spells = spells;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.spells.size());
        this.spells.forEach(buffer::writeString);
    }

    public static ShoutIncantationPack decode(PacketBuffer buffer) {
        List<String> spells = new ArrayList<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            spells.add(buffer.readString());
        }
        return new ShoutIncantationPack(spells);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
                WandItem.setWandSpells(stack, new SpellSet(this.spells.stream().map(SpellRegistry::getByIncantation).filter(Objects::nonNull).collect(Collectors.toList())));
                player.sendContainerToPlayer(player.container);
            }
        });
        context.get().setPacketHandled(true);
    }
}
