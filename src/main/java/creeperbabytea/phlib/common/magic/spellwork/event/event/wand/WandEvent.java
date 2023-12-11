package creeperbabytea.phlib.common.magic.spellwork.event.event.wand;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WandEvent extends LivingEvent {
    public WandEvent(LivingEntity player) {
        super(player);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
