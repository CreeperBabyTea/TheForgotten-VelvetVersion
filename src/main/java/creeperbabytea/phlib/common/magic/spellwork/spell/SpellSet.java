package creeperbabytea.phlib.common.magic.spellwork.spell;

import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SpellSet implements INBTSerializable<ListNBT> {
    private final List<Spell> spells = new ArrayList<>();

    public SpellSet(List<Spell> spells) {
        if (spells.size() > 4)
            spells.subList(0, 4);
        this.spells.addAll(spells);
    }

    public SpellSet() {
    }

    public SpellSet add(Spell spell) {
        this.spells.add(spell);
        return this;
    }

    public SpellSet remove(Spell spell) {
        this.spells.remove(spell);
        return this;
    }

    public boolean isEmpty() {
        return this.spells.isEmpty();
    }

    public Stream<Spell> stream() {
        return this.spells.stream();
    }

    public void forEach(Consumer<Spell> action) {
        this.spells.forEach(action);
    }

    @Override
    public ListNBT serializeNBT() {
        ListNBT list = new ListNBT();
        spells.forEach(spell -> list.add(StringNBT.valueOf(Objects.requireNonNull(spell.getRegistryName()).toString())));
        return list;
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        deserializeNBT(nbt, (s) -> true);
    }

    public void deserializeNBT(ListNBT nbt, Predicate<Spell> filter) {
        nbt.forEach(id -> {
            Spell spell = SpellRegistry.getById(id.getString());
            if (spell != null && filter.test(spell))
                spells.add(spell);
        });
    }
}
