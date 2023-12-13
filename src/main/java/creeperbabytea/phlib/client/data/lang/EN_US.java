package creeperbabytea.phlib.client.data.lang;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.tealib.registry.GeneralRegister;
import creeperbabytea.tealib.util.data.ILanguageHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class EN_US extends LanguageProvider implements ILanguageHelper {
    public EN_US(DataGenerator gen) {
        super(gen, TheForgotten.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        GeneralRegister reg = PhilosophersObjects.GENERAL;
        for (Spell spellObj : PhilosophersObjects.SPELLS.getEntries()) {
            spell(spellObj);
        }

        add("gui.the_forgotten.item.scroll", "Scroll");
    }

    private void spell(Spell spell) {
        add("incantation." + spell.getIncantation(), format(spell.getIncantation()));
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
