package creeperbabytea.fgt.common.magic.spellwork.network;

import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectScrollSlotPack {
    private final int xIndex;
    private final int yIndex;

    public SelectScrollSlotPack(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(xIndex);
        buffer.writeInt(yIndex);
    }

    public static SelectScrollSlotPack decode(PacketBuffer buffer) {
        int xIndex = buffer.readInt();
        int yIndex = buffer.readInt();
        return new SelectScrollSlotPack(xIndex, yIndex);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            assert player != null;
            ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
            if (!(stack.getItem() instanceof ScrollItem))
                return;
            ScrollItem.setCurrentSlot(stack, xIndex, yIndex);
        });
        context.get().setPacketHandled(true);
    }
}
