package creeperbabytea.phlib.common.registry;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpellRegistry {
    private static final Supplier<IForgeRegistry<Spell>> REGISTRY = PhilosophersObjects.SPELLS.makeReg("spell", new RegistryBuilder<>());
    private static final Map<String, Spell> byIncantations = new HashMap<>();

    @Nullable
    public static Spell getById(ResourceLocation key) {
        return REGISTRY.get().getValue(key);
    }

    @Nullable
    public static Spell getById(String key) {
        return getById(new ResourceLocation(key));
    }

    @Nullable
    public static Spell getByIncantation(String incantation) {
        return byIncantations.get(format(incantation));
    }

    public static void init() {
    }

    public static void reloadMap() {
        REGISTRY.get().forEach(spell -> byIncantations.put(spell.getIncantation(), spell));
    }

    public static String format(String unformatted) {
        return unformatted.toLowerCase().replaceAll("\\s+", "_").replaceAll("_+", "_");
    }
}
