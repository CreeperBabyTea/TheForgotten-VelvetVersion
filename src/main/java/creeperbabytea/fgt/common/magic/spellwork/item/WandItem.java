package creeperbabytea.fgt.common.magic.spellwork.item;

import creeperbabytea.fgt.common.init.magic.MagicObjects;
import creeperbabytea.fgt.common.magic.spellwork.Util;
import creeperbabytea.fgt.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.fgt.common.magic.spellwork.event.event.wand.WandCastEvent;
import creeperbabytea.fgt.common.magic.spellwork.item.wand.WandMaterial;
import creeperbabytea.fgt.common.magic.spellwork.item.wand.WandState;
import creeperbabytea.fgt.common.magic.spellwork.spell.*;
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
import java.util.function.Predicate;

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
        return stack.getOrCreateTag().getBoolean("chargeable") ? UseAction.SPEAR : UseAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND)
            return ActionResult.resultFail(playerIn.getHeldItem(handIn));
        ItemStack wand = playerIn.getHeldItem(handIn);

        SpellSet set = Util.getSpells(playerIn, spell -> spell instanceof IChargeableSpell);
        wand.getOrCreateTag().putBoolean("chargeable", !set.isEmpty());

        float multiplier = this.getMultiplier(wand, worldIn, playerIn);

        if (this.getUseAction(wand) == UseAction.SPEAR) {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(wand);
        } else {
            this.castInstant(playerIn, multiplier);
            return ActionResult.resultSuccess(wand);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (player.world.isRemote()) {
            Util.getSpells(player, spell -> spell instanceof IChargeableSpell).forEach(spell -> ((IChargeableSpell) spell).drawChargeEffect(stack, player, getUseDuration(stack) - count));
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack wand, World worldIn, LivingEntity entityLiving, int timeLeft) {
        int duration = Math.min(60, this.getUseDuration(wand) - timeLeft);
        if (duration <= 10)
            return;
        float multiplier = this.getMultiplier(wand, worldIn, entityLiving);
        this.castCharged(entityLiving, duration, multiplier);
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
    private void castInstant(LivingEntity caster, float multiplier) {
        this.castSpells(caster, spell -> new SpellEntry(spell, -1, multiplier));
    }

    /**
     * Spells that require a charge
     */
    private void castCharged(LivingEntity caster, int chargeDuration, float multiplier) {
        this.castSpells(caster, spell -> new SpellEntry(spell, chargeDuration, multiplier));
    }

    private void castSpells(LivingEntity caster, Function<Spell, SpellEntry> toEntry) {
        SpellEntry[] throwableSpells = Util.getSpells(caster, spell -> spell instanceof ThrowableSpell).stream().map(toEntry).toArray(SpellEntry[]::new);
        SpellEntry[] localSpells = Util.getSpells(caster, spell -> !(spell instanceof ThrowableSpell)).stream().map(toEntry).toArray(SpellEntry[]::new);

        WandCastEvent.Pre pre = new WandCastEvent.Pre(caster, throwableSpells, localSpells);
        MinecraftForge.EVENT_BUS.post(pre);
        if (!pre.isCanceled()) {
            float step = 0;
            if (!pre.isThrowableCancelled()) {
                SpellEntity spellEntity = new SpellEntity(caster, throwableSpells);
                spellEntity.cast(4.0F);
                for (SpellEntry entry : throwableSpells) {
                    entry.get().onLocalCast(caster, entry.intensity());
                    step += (float) (entry.get().getMagicState().getComplexity() * 0.5F + entry.intensity() + 0.5);
                }
            }
            if (!pre.isLocalCancelled()) {
                for (SpellEntry entry : localSpells) {
                    entry.get().onLocalCast(caster, entry.intensity());
                    step += (float) (entry.get().getMagicState().getComplexity() * 0.5F + entry.intensity() + 0.5);
                }
            }
            Util.promoteProficiency(caster, step);
            System.out.println(Util.getProficiency(caster));

            WandCastEvent.Post post = new WandCastEvent.Post(caster, throwableSpells, localSpells);
            MinecraftForge.EVENT_BUS.post(post);
        }
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
        return new WandState(stack.getOrCreateTag().getCompound("state"));
    }

    public static void setWandSpells(ItemStack stack, SpellSet spells) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.remove("spells");
        nbt.put("spells", spells.serializeNBT());
    }

    public static SpellSet getSpellsFrom(ItemStack stack, Predicate<Spell> filter) {
        SpellSet spells = new SpellSet();
        spells.deserializeNBT(stack.getOrCreateTag().getList("spells", 8), filter);

        return spells;
    }
}
