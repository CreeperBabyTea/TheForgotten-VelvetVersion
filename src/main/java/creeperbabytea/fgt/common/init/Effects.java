package creeperbabytea.fgt.common.init;

import creeperbabytea.fgt.common.ForgottenObjects;
import creeperbabytea.fgt.common.spell.LuminousSpell;
import creeperbabytea.tealib.common.registry.TeaGeneralRegister;
import net.minecraft.potion.Effect;

public class Effects extends net.minecraft.potion.Effects {
    public static final Effect LUMINOUS_MARK;

    public static void init() {
    }

    static {
        TeaGeneralRegister reg = ForgottenObjects.GENERAL;
        reg.bindSuperClass(Effect.class);
        LUMINOUS_MARK = reg.add("luminous_mark", new LuminousSpell.LuminousMarkEffect());
    }
}
