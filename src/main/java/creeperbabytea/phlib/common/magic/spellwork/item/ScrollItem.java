package creeperbabytea.phlib.common.magic.spellwork.item;

import creeperbabytea.phlib.TheForgotten;
import creeperbabytea.phlib.client.screen.spellwork.ScrollGUI;
import creeperbabytea.phlib.common.init.magic.MagicObjects;
import creeperbabytea.tealib.client.screen.ITeaScreenProperties;
import creeperbabytea.tealib.client.screen.ScreenOpener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ScrollItem extends Item {
    private final IScrollType type;

    public ScrollItem(IScrollType type) {
        super(new Properties().group(MagicObjects.MAGIC_GROUP).maxStackSize(1));
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ScreenOpener.open(new ScrollGUI(type)));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public interface IScrollType extends ITeaScreenProperties {
        ResourceLocation getBackgroundTexture();

        int xSize();

        int ySize();
    }

    public enum EnumScrollTypes implements IScrollType {
        BAMBOO(new TranslationTextComponent("gui.the_forgotten.item.scroll"), TheForgotten.INSTANCE.modLoc("textures/gui/magic/spellwork/scroll.png"), 5, 5),
        ;

        final ITextComponent title;
        final ResourceLocation bg;
        final int xSize, ySize;

        EnumScrollTypes(ITextComponent title, ResourceLocation bg, int xSize, int ySize) {
            this.title = title;
            this.bg = bg;
            this.xSize = Math.min(xSize, 5);
            this.ySize = Math.min(ySize, 5);
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
