package creeperbabytea.fgt.client.data.lang;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.tealib.common.registry.TeaGeneralRegister;
import creeperbabytea.tealib.util.data.ILanguageHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Objects;

public class EN_US extends LanguageProvider implements ILanguageHelper {
    public EN_US(DataGenerator gen) {
        super(gen, TheForgotten.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        TeaGeneralRegister reg = ForgottenObjects.GENERAL;
        for (Item item : reg.getRegister(Item.class).getEntries())
            add(item, format(Objects.requireNonNull(item.getRegistryName()).getPath()));
        for (Spell spellObj : ForgottenObjects.SPELLS.getEntries()) {
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
