package creeperbabytea.fgt.common.magic.spellwork.network;

import creeperbabytea.fgt.common.magic.spellwork.item.scroll.ScrollSlot;
import creeperbabytea.fgt.common.magic.spellwork.item.scroll.ScrollState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SaveScrollPack {
    private final boolean isMainHand;
    private final int xIndex;
    private final int yIndex;
    private final List<String> spells;

    public SaveScrollPack(boolean isMainHand, int xIndex, int yIndex, List<String> spells) {
        this.isMainHand = isMainHand;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.spells = spells;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(isMainHand);
        buffer.writeInt(xIndex);
        buffer.writeInt(yIndex);
        buffer.writeInt(this.spells.size());
        this.spells.forEach(buffer::writeString);
    }

    public static SaveScrollPack decode(PacketBuffer buffer) {
        boolean isMainHand = buffer.readBoolean();
        int xIndex = buffer.readInt();
        int yIndex = buffer.readInt();
        List<String> spells = new ArrayList<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            spells.add(buffer.readString());
        }
        return new SaveScrollPack(isMainHand, xIndex, yIndex, spells);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                ScrollState.from(player.getHeldItem(isMainHand ? Hand.MAIN_HAND : Hand.OFF_HAND)).putSlot(xIndex, yIndex, new ScrollSlot(spells.toArray(new String[0])));
                player.sendContainerToPlayer(player.container);
            }
        });
        context.get().setPacketHandled(true);
    }
}
