package creeperbabytea.phlib.client.data.lang;

import creeperbabytea.phlib.TheForgotten;
import static creeperbabytea.phlib.common.init.magic.Spells.*;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.tealib.util.data.ILanguageHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

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
        spell(TELEPORTING_SPELL, "幻影移形");

        spell(LUMINOUS_SPELL, "荧光闪烁");
        spell(SLOW_FALLING_CHARM, "我似浮云");
    }

    private void spell(Spell spell, String value) {
        add("incantation."+ spell.getIncantation(), value);
    }
}
