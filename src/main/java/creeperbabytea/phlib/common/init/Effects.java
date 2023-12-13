package creeperbabytea.phlib.common.init;

import creeperbabytea.phlib.common.PhilosophersObjects;
import creeperbabytea.phlib.common.spell.LuminousSpell;
import net.minecraft.potion.Effect;

public class Effects extends net.minecraft.potion.Effects {
    public static final Effect LUMINOUS_MARK = PhilosophersObjects.EFFECTS.add("luminous_mark", new LuminousSpell.LuminousMarkEffect());

    public static void init() {
    }
}
