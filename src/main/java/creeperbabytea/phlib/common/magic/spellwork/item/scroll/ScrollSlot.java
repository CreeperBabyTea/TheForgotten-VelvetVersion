package creeperbabytea.phlib.common.magic.spellwork.item.scroll;

import creeperbabytea.phlib.common.magic.spellwork.spell.SpellSet;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScrollSlot implements INBTSerializable<ListNBT> {
    private String[] spells;

    public ScrollSlot(String... spellIn) {
        if (spellIn.length >4)
            spellIn = Arrays.copyOf(spellIn, 4);
        this.spells = Arrays.stream(spellIn).toArray(String[]::new);
    }

    public List<String> getSpells() {
        return Arrays.stream(spells).collect(Collectors.toList());
    }

    @Override
    public ListNBT serializeNBT() {
        ListNBT list = new ListNBT();
        Arrays.stream(this.spells).forEachOrdered(s -> list.add(StringNBT.valueOf(s)));
        return list;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        if (nbt.size() >4)
            nbt = (ListNBT) nbt.subList(0,4);
        List<String> str = new ArrayList<>();
        nbt.forEach(n -> str.add(n.getString()));
        this.spells = str.toArray(new String[0]);
    }
}
