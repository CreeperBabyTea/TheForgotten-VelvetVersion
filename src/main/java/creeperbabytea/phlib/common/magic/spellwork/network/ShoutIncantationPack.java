package creeperbabytea.phlib.common.magic.spellwork.network;

import creeperbabytea.phlib.common.magic.spellwork.item.WandItem;
import creeperbabytea.phlib.common.registry.SpellRegistry;
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
    private final UUID owner;
    private final List<String> spells;

    public ShoutIncantationPack(UUID owner, List<String> spells) {
        this.owner = owner;
        this.spells = spells;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeUniqueId(this.owner);
        buffer.writeInt(this.spells.size());
        this.spells.forEach(buffer::writeString);
    }

    public static ShoutIncantationPack decode(PacketBuffer buffer) {
        UUID owner = buffer.readUniqueId();
        List<String> spells = new ArrayList<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            spells.add(buffer.readString());
        }
        return new ShoutIncantationPack(owner, spells);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                ItemStack wand = player.getHeldItem(Hand.MAIN_HAND);
                WandItem.setSpells(wand, this.spells.stream().map(SpellRegistry::getByIncantation).filter(Objects::nonNull).collect(Collectors.toList()));
                player.sendContainerToPlayer(player.container);
            }
        });
        context.get().setPacketHandled(true);
    }
}
