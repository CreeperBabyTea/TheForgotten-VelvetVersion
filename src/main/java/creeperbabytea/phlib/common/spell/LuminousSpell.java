package creeperbabytea.phlib.common.spell;

import creeperbabytea.phlib.common.magic.spellwork.SpellState;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.LightType;

public class LuminousSpell extends Spell {
    public LuminousSpell() {
        super("luminous", new SpellState(0.0F, 0.0F));
    }

    @Override
    public void onLocalCast(PlayerEntity player, float intensity) {
        //TODO
    }
}
