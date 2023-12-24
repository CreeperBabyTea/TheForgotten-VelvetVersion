package creeperbabytea.fgt.common.magic.spellwork.event.event.wand;

import creeperbabytea.fgt.common.magic.spellwork.item.ScrollItem;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellEntry;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellSet;
import creeperbabytea.fgt.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.tealib.util.TArrays;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.Arrays;

public class WandCastEvent extends WandEvent {
    protected SpellEntry[] spells;
    protected SpellEntry[] throwables;
    protected SpellEntry[] locals;

    private boolean isThrowableCancelled = false;
    private boolean isLocalCancelled = false;

    public WandCastEvent(LivingEntity player, SpellEntry[] throwables, SpellEntry[] locals) {
        super(player, player.getHeldItem(Hand.MAIN_HAND), player.getHeldItem(Hand.OFF_HAND));
        this.throwables = throwables;
        this.locals = locals;
        this.spells = TArrays.append(throwables, locals);
    }

    public WandCastEvent(LivingEntity player, ItemStack wand, ItemStack scroll, SpellEntry[] throwables, SpellEntry[] locals) {
        super(player, wand, scroll);
        this.throwables = throwables;
        this.locals = locals;
        this.spells = TArrays.append(throwables, locals);
    }

    public void setThrowableCancelled(boolean throwableCancelled) {
        this.isThrowableCancelled = throwableCancelled;
    }

    public boolean isThrowableCancelled() {
        return isThrowableCancelled;
    }

    public void setLocalCancelled(boolean localCancelled) {
        isLocalCancelled = localCancelled;
    }

    public boolean isLocalCancelled() {
        return isLocalCancelled;
    }

    public SpellEntry[] getLocals() {
        return locals;
    }

    public SpellEntry[] getThrowables() {
        return throwables;
    }

    public SpellEntry[] getSpells() {
        return spells;
    }

    @Cancelable
    public static class Pre extends WandCastEvent {
        public Pre(LivingEntity player, SpellEntry[] throwables, SpellEntry[] locals) {
            super(player, throwables, locals);
        }

        public Pre(LivingEntity player, ItemStack wand, ItemStack scroll, SpellEntry[] spells) {
            super(player, wand, scroll,
                    Arrays.stream(spells).filter(e -> e.get() instanceof ThrowableSpell).toArray(SpellEntry[]::new),
                    Arrays.stream(spells).filter(e -> !(e.get() instanceof ThrowableSpell)).toArray(SpellEntry[]::new));
        }
    }

    public static class Post extends WandCastEvent {
        public Post(LivingEntity player, SpellEntry[] throwables, SpellEntry[] locals) {
            super(player, throwables, locals);
        }

        public Post(LivingEntity player, ItemStack wand, ItemStack scroll, SpellEntry[] spells) {
            super(player, wand, scroll,
                    Arrays.stream(spells).filter(e -> e.get() instanceof ThrowableSpell).toArray(SpellEntry[]::new),
                    Arrays.stream(spells).filter(e -> !(e.get() instanceof ThrowableSpell)).toArray(SpellEntry[]::new));
        }
    }
}
