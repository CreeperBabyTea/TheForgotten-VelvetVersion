package creeperbabytea.fgt.common.magic.spellwork.item;

import creeperbabytea.fgt.TheForgotten;
import creeperbabytea.fgt.client.screen.spellwork.ScrollGUI;
import creeperbabytea.fgt.common.init.magic.MagicObjects;
import creeperbabytea.fgt.common.magic.spellwork.item.scroll.ScrollState;
import creeperbabytea.fgt.common.magic.spellwork.spell.Spell;
import creeperbabytea.fgt.common.magic.spellwork.spell.SpellSet;
import creeperbabytea.fgt.common.registry.SpellRegistry;
import creeperbabytea.tealib.client.screen.ITeaScreenProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ScrollItem extends Item {
    private final IScrollType type;

    public ScrollItem(@Nullable IScrollType type) {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack scroll = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote && scroll.getItem() instanceof ScrollItem)
            Minecraft.getInstance().displayGuiScreen(new ScrollGUI(type, scroll, handIn));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static SpellSet getSpellsFrom(ItemStack stack, Predicate<Spell> filter) {
        if (!(stack.getItem() instanceof ScrollItem))
            return new SpellSet();

        ScrollState scroll = ScrollState.from(stack);
        int x = stack.getOrCreateTag().getInt("current_x");
        int y = stack.getOrCreateTag().getInt("current_y");
        return new SpellSet(scroll.getSlot(x, y).getSpells().stream().map(SpellRegistry::getByIncantation).filter(filter).collect(Collectors.toList()));
    }

    public static void setCurrentSlot(ItemStack scroll, int x, int y) {
        Item item = scroll.getItem();
        if (!(item instanceof ScrollItem))
            return;
        CompoundNBT tag = scroll.getOrCreateTag();
        if (((ScrollItem) item).getType().xSize() >= x)
            tag.putInt("current_x", x);
        if (((ScrollItem) item).getType().ySize() >= y)
            tag.putInt("current_y", y);
    }

    //TODO: 完善一下向量相关的数学支持
    public static int[] getCurrentSlot(ItemStack scroll) {
        CompoundNBT tag = scroll.getOrCreateTag();
        return new int[]{tag.getInt("current_x"), tag.getInt("current_y")};
    }

    public IScrollType getType() {
        return type;
    }

    public interface IScrollType extends ITeaScreenProperties {
        ResourceLocation getBackgroundTexture();

        int xSize();

        int ySize();
    }

    public static class ScrollType implements IScrollType {
        public static final ScrollType BAMBOO = new ScrollType(new TranslationTextComponent("gui.the_forgotten.item.scroll.bamboo"), TheForgotten.modLocation("textures/gui/magic/spellwork/scroll.png"), 5, 5);

        final ITextComponent title;
        final ResourceLocation bg;
        final int xSize, ySize;

        ScrollType(ITextComponent title, ResourceLocation bg, int xSize, int ySize) {
            this.title = title;
            this.bg = bg;
            this.xSize = Math.min(xSize, 4);
            this.ySize = Math.min(ySize, 4);
        }

        @Override
        public ResourceLocation getBackgroundTexture() {
            return bg;
        }

        @Override
        public int xSize() {
            return xSize;
        }

        @Override
        public int ySize() {
            return ySize;
        }

        @Override
        public ITextComponent getTitle() {
            return title;
        }
    }
}
