package creeperbabytea.phlib.client.data.lang;

import creeperbabytea.phlib.TheForgotten;
import static creeperbabytea.phlib.common.init.Spells.*;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.tealib.util.data.ILanguageHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class ZH_CN extends LanguageProvider implements ILanguageHelper {
    public ZH_CN(DataGenerator gen) {
        super(gen, TheForgotten.MODID, "zh_cn");
    }

    @Override
    public String modId() {
        return TheForgotten.MODID;
    }

    @Override
    protected void addTranslations() {
        spell(KILLING_CURSE, "阿瓦达啃大瓜");
        spell(EXPLOSION_SPELL, "爆破");
        spell(CAT_TRANSFORM_CHARM, "变猫娘");
    }

    private void spell(Supplier<? extends Spell> spell, String value) {
        add("incantation."+ spell.get().getIncantation(), value);
    }
}
