package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.common.init.MagicObjects;
import creeperbabytea.phlib.common.magic.spellwork.SpellEntry;
import creeperbabytea.phlib.common.magic.spellwork.entity.SpellEntity;
import creeperbabytea.phlib.common.magic.spellwork.event.event.wand.WandCastEvent;
import creeperbabytea.phlib.common.magic.spellwork.item.wand.WandMaterial;
import creeperbabytea.phlib.common.magic.spellwork.item.wand.WandState;
import creeperbabytea.phlib.common.magic.spellwork.spell.IChargeableSpell;
import creeperbabytea.phlib.common.magic.spellwork.spell.Spell;
import creeperbabytea.phlib.common.magic.spellwork.spell.ThrowableSpell;
import creeperbabytea.phlib.common.registry.SpellRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.*;
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
        return getSpells(stack, spell -> spell instanceof IChargeableSpell).isEmpty() ? UseAction.NONE : UseAction.SPEAR;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND)
            return ActionResult.resultFail(playerIn.getHeldItem(handIn));
        ItemStack wand = playerIn.getHeldItem(handIn);
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
        if (player.world.isRemote())
            getSpells(stack, spell -> true).forEach(spell -> spell.drawCastEffect(stack, player, getUseDuration(stack) - count));
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

    /** Detects spell strength multiples caused by external factors, such as the user's identity. */
    private float getMultiplier(ItemStack wand, World world, LivingEntity entity) {
        UUID owner = getOwner(wand);
        System.out.println(owner);
        if (owner == null) {
            setOwner(wand, entity.getUniqueID());
            return 0.75F;
        }
        else {
            if(owner == entity.getUniqueID()) {
                return 1.0F;
            } else {
                return 1.0F - getState(wand).getLoyalty();
            }
        }
    }

    /** Instantaneous spells that don't need to be charged */
    private void castInstant(ItemStack wand, LivingEntity caster, float multiplier) {
        this.castSpells(wand, caster, -1, multiplier, spell -> new SpellEntry(spell, -1, multiplier));
    }

    /** Spells that require a charge */
    private void castCharged(ItemStack wand, LivingEntity caster, int chargeDuration, float multiplier) {
        this.castSpells(wand, caster, chargeDuration, multiplier, spell -> new SpellEntry(spell, chargeDuration, multiplier));
    }

    private void castSpells(ItemStack wand, LivingEntity caster, int chargeDuration, float multiplier, Function<Spell, SpellEntry> mapping) {
        WandCastEvent.Pre pre = new WandCastEvent.Pre(caster, wand);
        MinecraftForge.EVENT_BUS.post(pre);
        if (pre.isCanceled())
            return;

        SpellEntry[] spells = getSpells(wand, spell -> spell instanceof ThrowableSpell).stream().map(mapping).toArray(SpellEntry[]::new);

        Arrays.stream(spells).forEach(entry -> entry.get().influenceOnCaster(caster, entry.intensity()));

        SpellEntity spellEntity = new SpellEntity(caster, spells);
        spellEntity.cast(caster, 4.0F);

        WandCastEvent.Post post = new WandCastEvent.Post(caster, wand);
        MinecraftForge.EVENT_BUS.post(post);
    }

    public static void setSpells(ItemStack wand, List<Spell> spells) {
        ListNBT list = new ListNBT();
        spells.forEach(spell -> list.add(StringNBT.valueOf(Objects.requireNonNull(spell.getRegistryName()).toString())));
        CompoundNBT nbt = wand.getOrCreateTag();
        nbt.remove("spells");
        nbt.put("spells", list);
    }

    private static List<Spell> getSpells(ItemStack wand, Predicate<Spell> predicate) {
        List<Spell> spells = new ArrayList<>();
        if (wand.getTag() != null) {
            ListNBT list = wand.getTag().getList("spells", 8);
            list.forEach(id -> {
                Spell spell = SpellRegistry.getById(id.getString());
                if (spell != null && predicate.test(spell))
                    spells.add(spell);
            });
        }
        return spells;
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
