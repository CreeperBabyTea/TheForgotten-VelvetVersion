package creeperbabytea.phlib.client.data.lang;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.tealib.registry.GeneralDeferredRegister;
import creeperbabytea.tealib.util.data.ILanguageHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class EN_US extends LanguageProvider implements ILanguageHelper {
    public EN_US(DataGenerator gen) {
        super(gen, TheForgotten.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        GeneralDeferredRegister reg = PhilosophersObjects.GENERAL;
        for (RegistryObject<Spell> spellObj : PhilosophersObjects.SPELLS.getEntries()) {
            spell(spellObj);
        }
    }

    private void spell(Supplier<Spell> spell) {
        add("incantation."+ spell.get().getIncantation(), format(spell.get().getIncantation()));
    }

    @Override
    public String getName() {
        return TheForgotten.MODID + ":translation.en_us";
    }

    @Override
    public String modId() {
        return TheForgotten.MODID;
    }
}
