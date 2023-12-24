package creeperbabytea.fgt.client.data.lang;

import creeperbabytea.fgt.TheForgotten;
import static creeperbabytea.fgt.common.init.magic.Spells.*;

import creeperbabytea.fgt.common.init.magic.MagicObjects;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
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
        add(MagicObjects.WAND, "魔杖");
        add(MagicObjects.SCROLL, "卷轴");

        spell(KILLING_CURSE, "阿瓦达索命");
        spell(EXPLOSION_SPELL, "爆破");
        spell(CAT_TRANSFORM_CHARM, "变猫娘");
        spell(TELEPORTING_SPELL, "幻影移形");

        spell(LUMINOUS_SPELL, "荧光闪烁");
        spell(SLOW_FALLING_CHARM, "我似浮云");

        add("gui.the_forgotten.item.scroll", "卷轴");
    }

    private void spell(Spell spell, String value) {
        add("incantation."+ spell.getIncantation(), value);
    }
}
