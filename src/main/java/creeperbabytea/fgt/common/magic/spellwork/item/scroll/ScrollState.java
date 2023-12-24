package creeperbabytea.fgt.common.magic.spellwork.item.scroll;

import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ScrollState implements INBTSerializable<CompoundNBT> {
    private final ItemStack scroll;

    private int xSize;
    private int ySize;
    private ScrollSlot[][] slots;

    private ScrollState(ItemStack scroll) {
        Item item = scroll.getItem();
        if (!(item instanceof ScrollItem))
            throw new ClassCastException("Item must be a ScrollItem!");
        this.scroll = scroll;

        ScrollItem.IScrollType type = ((ScrollItem) item).getType();
        CompoundNBT nbt = scroll.getTag();
        if (nbt != null && nbt.contains("scroll"))
            this.deserializeNBT(nbt.getCompound("scroll"));
        else {
            this.xSize = type.xSize();
            this.ySize = type.ySize();
            this.slots = new ScrollSlot[xSize][ySize];
            scroll.getOrCreateTag().put("scroll", this.serializeNBT());
        }
    }

    public static ScrollState from(ItemStack scroll) {
        return new ScrollState(scroll);
    }

    public void putSlot(int x, int y, ScrollSlot slot) {
        this.slots[x][y] = slot;
        CompoundNBT nbt = this.scroll.getOrCreateTag();
        nbt.remove("scroll");
        nbt.put("scroll", this.serializeNBT());
    }

    public ScrollSlot getSlot(int x, int y) {
        return this.slots[x][y];
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("x_size", xSize);
        nbt.putInt("y_size", ySize);
        for (int i = 0; i < this.slots.length; i++) {
            for (int j = 0; j < this.slots[i].length; j++) {
                if (slots[i][j] == null)
                    slots[i][j] = new ScrollSlot();
                nbt.put("slot_" + i + '_' + j, slots[i][j].serializeNBT());
            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.xSize = nbt.getInt("x_size");
        this.ySize = nbt.getInt("y_size");
        this.slots = new ScrollSlot[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                slots[i][j] = new ScrollSlot();
                slots[i][j].deserializeNBT(nbt.getList("slot_" + i + '_' + j, 8));
            }
        }
    }
}
