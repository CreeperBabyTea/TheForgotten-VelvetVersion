package creeperbabytea.phlib.common.magic.warecraft.tileentity;

import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.magic.warecraft.item.EnchantDictionaryItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TableOfEnchantmentsTileEntity extends TileEntity {
    private boolean hasDictionary = false;
    private ItemStack dictionary = null;

    public TableOfEnchantmentsTileEntity() {
        super(MagicObjects.TABLE_OF_ENCHANTMENTS_TE.get());
    }

    public void putDictionary(ItemStack dictionary) {
        if (!this.hasDictionary) {
            this.hasDictionary = true;
            this.dictionary = dictionary;
            this.markDirty();
        }
    }

    public ItemStack takeDictionary() {
        ItemStack stack = this.dictionary.copy();
        this.hasDictionary = false;
        this.dictionary = null;
        this.markDirty();
        return stack;
    }

    public boolean hasDictionary() {
        return hasDictionary;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.hasDictionary = nbt.getBoolean("dictionary");
        if (this.hasDictionary)
            this.dictionary = ItemStack.read(nbt.getCompound("dictionary"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("dictionary", this.hasDictionary);
        if (this.hasDictionary)
            compound.put("dictionary", this.dictionary.serializeNBT());
        return compound;
    }
}
