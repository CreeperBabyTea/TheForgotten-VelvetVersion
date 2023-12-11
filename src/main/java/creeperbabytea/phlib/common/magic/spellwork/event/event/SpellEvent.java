package creeperbabytea.phlib.common.magic.spellwork.event.event;

import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import net.minecraftforge.event.entity.EntityEvent;

public class SpellEvent extends EntityEvent {
    public SpellEvent(SpellEntity spell) {
        super(spell);
    }

    public static class SpellCastEvent extends SpellEvent {

        public SpellCastEvent(SpellEntity spell) {
            super(spell);
        }
    }
}
