package creeperbabytea.fgt.common.registry;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpellRegistry {
    private static final Supplier<IForgeRegistry<Spell>> REGISTRY = ForgottenObjects.SPELLS.makeReg("spell", new RegistryBuilder<>());
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
