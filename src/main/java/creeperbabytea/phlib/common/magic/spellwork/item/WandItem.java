package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.common.init.magic.MagicObjects;
import creeperbabytea.phlib.common.magic.spellwork.SpellEntry;
import creeperbabytea.phlib.common.magic.spellwork.Util;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.event.event.wand.WandCastEvent;
import creeperbabytea.phlib.common.magic.spellwork.item.wand.WandMaterial;
import creeperbabytea.phlib.common.magic.spellwork.item.wand.WandState;
import creeperbabytea.phlib.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.SpellSet;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Function;

public class WandItem extends Item {
    public WandItem() {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(makeWand(new WandMaterial.WandWood(1, 0.2F, 122), new WandMaterial.WandCore(1F, 0.7F, 131)));
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("charge") ? UseAction.SPEAR : UseAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND)
            return ActionResult.resultFail(playerIn.getHeldItem(handIn));
        ItemStack wand = playerIn.getHeldItem(handIn);

        if (playerIn.getHeldItem(Hand.OFF_HAND).getItem() instanceof ScrollItem)
            wand.getOrCreateTag().putBoolean("charge", !Util.getScrollSpells(playerIn.getHeldItem(Hand.OFF_HAND), spell -> spell instanceof IChargeableSpell).isEmpty());
        else
            wand.getOrCreateTag().putBoolean("charge", !Util.getWandSpells(wand, spell -> spell instanceof IChargeableSpell).isEmpty());

        float multiplier = this.getMultiplier(wand, worldIn, playerIn);

        if (this.getUseAction(wand) == UseAction.SPEAR) {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(wand);
        } else {
            this.castInstant(wand, playerIn, multiplier);
            return ActionResult.resultSuccess(wand);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player.world.isRemote()) {
            SpellSet spells;
            if (player.getHeldItem(Hand.OFF_HAND).getItem() instanceof ScrollItem)
                spells = Util.getScrollSpells(player.getHeldItem(Hand.OFF_HAND), spell -> spell instanceof IChargeableSpell);
            else
                spells = Util.getWandSpells(stack, spell -> spell instanceof IChargeableSpell);
            spells.forEach(spell -> ((IChargeableSpell) spell).drawChargeEffect(stack, player, getUseDuration(stack) - count));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        int duration = Math.min(60, this.getUseDuration(stack) - timeLeft);
        if (duration <= 10)
            return;
        float multiplier = this.getMultiplier(stack, worldIn, entityLiving);
        this.castCharged(stack, entityLiving, duration, multiplier);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Detects spell strength multiples caused by external factors, such as the user's identity.
     */
    private float getMultiplier(ItemStack wand, World world, LivingEntity entity) {
        UUID owner = getOwner(wand);
        if (owner == null) {
            setOwner(wand, entity.getUniqueID());
            return 0.75F;
        } else {
            if (owner == entity.getUniqueID()) {
                return 1.0F;
            } else {
                return 1.0F - getState(wand).getLoyalty();
            }
        }
    }

    /**
     * Instantaneous spells that don't need to be charged
     */
    private void castInstant(ItemStack wand, LivingEntity caster, float multiplier) {
        this.castSpells(wand, caster, spell -> new SpellEntry(spell, -1, multiplier));
    }

    /**
     * Spells that require a charge
     */
    private void castCharged(ItemStack wand, LivingEntity caster, int chargeDuration, float multiplier) {
        this.castSpells(wand, caster, spell -> new SpellEntry(spell, chargeDuration, multiplier));
    }

    private void castSpells(ItemStack wand, LivingEntity caster, Function<Spell, SpellEntry> toEntry) {
        WandCastEvent.Pre pre = new WandCastEvent.Pre(caster, wand);
        MinecraftForge.EVENT_BUS.post(pre);
        if (pre.isCanceled())
            return;

        SpellSet throwableSpells;
        SpellSet localSpells;

        if (caster.getHeldItem(Hand.OFF_HAND).getItem() instanceof ScrollItem) {
            ItemStack scroll = caster.getHeldItem(Hand.OFF_HAND);
            throwableSpells = Util.getScrollSpells(scroll, spell -> spell instanceof ThrowableSpell);
            localSpells = Util.getScrollSpells(scroll, spell -> !(spell instanceof ThrowableSpell));
        } else {
            throwableSpells = Util.getWandSpells(wand, spell -> spell instanceof ThrowableSpell);
            localSpells = Util.getScrollSpells(wand, spell -> !(spell instanceof ThrowableSpell));
        }

        SpellEntity spellEntity = new SpellEntity(caster, throwableSpells.stream().map(toEntry).toArray(SpellEntry[]::new));
        spellEntity.cast(4.0F);

        throwableSpells.stream().map(toEntry).forEach(entry -> entry.get().onLocalCast(caster, entry.intensity()));
        localSpells.stream().map(toEntry).forEach(entry -> entry.get().onLocalCast(caster, entry.intensity()));

        WandCastEvent.Post post = new WandCastEvent.Post(caster, wand);
        MinecraftForge.EVENT_BUS.post(post);
    }

    private static void setOwner(ItemStack wand, UUID owner) {
        CompoundNBT nbt = wand.getOrCreateTag();
        nbt.putUniqueId("owner", owner);
    }

    @Nullable
    public static UUID getOwner(ItemStack wand) {
        if (wand.getOrCreateTag().contains("owner"))
            return wand.getOrCreateTag().getUniqueId("owner");
        return null;
    }

    public static ItemStack makeWand(WandMaterial.WandWood wood, WandMaterial.WandCore... core) {
        ItemStack result = new ItemStack(MagicObjects.WAND);
        result.getOrCreateTag().put("state", new WandState(wood, core).serializeNBT());
        return result;
    }

    public static WandState getState(ItemStack stack) {
        return new WandState(stack);
    }
}
