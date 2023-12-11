package creeperbabytea.phlib.common.data.lang;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.tealib.registry.GeneralDeferredRegister;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class EN_US extends LanguageProvider {
    public EN_US(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        GeneralDeferredRegister reg = PhilosophersObjects.GENERAL;
        for (RegistryObject<Spell> spellObj : PhilosophersObjects.SPELLS.getEntries()) {
            add(spellObj.get().getIncantation(), spellObj.get().getIncantation());
        }
    }
}
